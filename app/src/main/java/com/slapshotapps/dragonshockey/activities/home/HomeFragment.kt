package com.slapshotapps.dragonshockey.activities.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.ActivityHomeBinding
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.coroutines.CoroutineContext


class HomeFragment : HockeyFragment(), CoroutineScope {

    private var hockeyScheduleSubscription: Subscription? = null
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var masterJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + masterJob


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        masterJob = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        masterJob.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_home, container, false)

        setHasOptionsMenu(true)

        val listener = actionBarListener
        listener?.setTitle(getString(R.string.dragons_hockey))

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firebaseDatabase = FirebaseDatabase.getInstance()


        try {
            firebaseDatabase!!.setPersistenceEnabled(true)
        } catch (exception: DatabaseException) {
            Timber.e("Unable to set persistance for Firebase")
        }
    }

    override fun onResume() {
        super.onResume()

        val listener = actionBarListener

        if(auth.currentUser == null){
            launch { checkUserAuth() }
        }else{
            updateData();
        }



        listener?.showProgressBar()
    }

    private suspend fun checkUserAuth() = coroutineScope {
        val signInResult = auth.signInAnonymously().await();

        withContext(Dispatchers.Main) {
            if (signInResult.user == null) {
                val listener = actionBarListener
                listener?.hideProgressBar()
                Toast.makeText(this@HomeFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                        .show()
            } else {
                updateData();
            }
        }
    }

    private fun updateData() {
        val listener = actionBarListener

        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { games -> HomeScreenObserver.getHomeScreen(firebaseDatabase, games, Date()) }
                .subscribe({ homeContents ->
                    binding.item = HomeScreenViewModel(homeContents)
                    listener?.hideProgressBar()
                }, {
                    binding.item = HomeScreenViewModel(null)
                    listener?.hideProgressBar()
                    Toast.makeText(this@HomeFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                            .show()
                })
    }

    override fun onPause() {
        super.onPause()

        if (hockeyScheduleSubscription != null) {
            hockeyScheduleSubscription!!.unsubscribe()
            hockeyScheduleSubscription = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            R.id.action_admin -> {
                startActivity(DragonsHockeyIntents.createAdminAuthIntent(context))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
