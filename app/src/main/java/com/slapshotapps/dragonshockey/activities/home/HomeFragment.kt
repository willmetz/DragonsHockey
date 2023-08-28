package com.slapshotapps.dragonshockey.activities.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents
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
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ActivityHomeBinding.inflate(inflater, container, false)

        val listener = actionBarListener
        listener?.setTitle(getString(R.string.dragons_hockey))

        setupMenu()

        return binding.root
    }

    private fun setupMenu(){
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_admin -> {
                        startActivity(DragonsHockeyIntents.createAdminAuthIntent(context))
                        true
                    }
                    else -> false
                }

            }
        }, viewLifecycleOwner,Lifecycle.State.RESUMED)
    }


    override fun onResumeWithCredentials() {
        updateData()
    }

    override fun noCredentialsOnResume() {
        val listener = actionBarListener
        listener?.hideProgressBar()
        Toast.makeText(this@HomeFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                .show()
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
        val listener = actionBarListener
        listener?.showProgressBar()
        super.onResume()
    }


    private fun updateData() {
        val listener = actionBarListener

        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { games ->
                    HomeScreenObserver.getHomeScreen(firebaseDatabase, games, Date())
                }
                .subscribe({ homeContents ->
                    //binding.item = HomeScreenViewModel(homeContents)
                    listener?.hideProgressBar()
                }, {
                    //binding.item = HomeScreenViewModel(null)
                    listener?.hideProgressBar()
                    Toast.makeText(this@HomeFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                            .show()
                })
    }

    override fun onPause() {
        super.onPause()
        hockeyScheduleSubscription?.unsubscribe()
        hockeyScheduleSubscription = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
