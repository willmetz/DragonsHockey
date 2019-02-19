package com.slapshotapps.dragonshockey.activities.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils
import com.slapshotapps.dragonshockey.activities.ActionBarListener
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.databinding.ActivityHomeBinding
import com.slapshotapps.dragonshockey.models.HomeContents
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import io.fabric.sdk.android.Fabric
import java.util.Date
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers
import timber.log.Timber

class HomeFragment : HockeyFragment() {

  private var hockeyScheduleSubscription: Subscription? = null
  private var binding: ActivityHomeBinding? = null


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.activity_home, container, false)

    return binding!!.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    firebaseDatabase = FirebaseDatabase.getInstance()


    try {
      firebaseDatabase!!.setPersistenceEnabled(true)
    } catch (exception: DatabaseException) {
      Timber.e("Unable to set persistance for Firebase")
    }

    val listener = actionBarListener
    listener?.setTitle(getString(R.string.dragons_hockey))
  }

  override fun onResume() {
    super.onResume()

    val listener = actionBarListener

    hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { games -> HomeScreenObserver.getHomeScreen(firebaseDatabase, games, Date()) }
        .subscribe({ homeContents ->
          binding!!.item = HomeScreenViewModel(homeContents)
          listener?.hideProgressBar()
        }, {
          binding!!.item = HomeScreenViewModel(null)
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
}
