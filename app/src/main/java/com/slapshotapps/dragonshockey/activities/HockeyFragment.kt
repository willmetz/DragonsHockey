package com.slapshotapps.dragonshockey.activities

import android.content.Context
import androidx.fragment.app.Fragment


abstract class HockeyFragment : Fragment() {

  protected var actionBarListener: ActionBarListener? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val hostActivity = activity
    if (hostActivity is ActionBarListener) {
      actionBarListener = hostActivity
    }
  }
}