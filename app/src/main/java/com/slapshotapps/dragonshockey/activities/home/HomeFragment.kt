package com.slapshotapps.dragonshockey.activities.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.ActivityHomeBinding
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*


class HomeFragment : HockeyFragment() {

    private var hockeyScheduleSubscription: Subscription? = null
    private lateinit var binding: ActivityHomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_home, container, false)

        setHasOptionsMenu(true)

        val listener = actionBarListener
        listener?.setTitle(getString(R.string.dragons_hockey))

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

        listener?.showProgressBar()
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
