package com.slapshotapps.dragonshockey.activities

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.AnalyticEventListener
import timber.log.Timber


abstract class HockeyFragment : Fragment() {

  protected var actionBarListener: ActionBarListener? = null
  protected var analyticEventListener: AnalyticEventListener? = null
  protected var firebaseDatabase: FirebaseDatabase? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val hostActivity = activity
    if (hostActivity is ActionBarListener) {
      actionBarListener = hostActivity
    }

    if (hostActivity is AnalyticEventListener) {
      analyticEventListener = hostActivity
    }

    firebaseDatabase = FirebaseDatabase.getInstance()

    try {
      firebaseDatabase!!.setPersistenceEnabled(true)
    } catch (exception: DatabaseException) {
      Timber.e("Unable to set persistance for Firebase")
    }
  }
}