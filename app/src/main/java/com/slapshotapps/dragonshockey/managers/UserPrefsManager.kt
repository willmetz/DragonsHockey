package com.slapshotapps.dragonshockey.managers

import android.content.Context
import android.content.SharedPreferences
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection

class UserPrefsManager(private val context: Context) {

  private val SHARED_PREFS_KEY = "HockeyPreferences"

  private val SORT_SELECTION_KEY = "sortSelection"

  var statSortPreference: StatSortSelection
    get() {
      val sortIndex = userPrefs.getInt(SORT_SELECTION_KEY, StatSortSelection.Points.index)

      return if (StatSortSelection.GamesPlayed.index == sortIndex) {
        StatSortSelection.GamesPlayed
      } else if (StatSortSelection.Goals.index == sortIndex) {
        StatSortSelection.Goals
      } else if (StatSortSelection.Assists.index == sortIndex) {
        StatSortSelection.Assists
      } else if (StatSortSelection.Points.index == sortIndex) {
        StatSortSelection.Points
      } else {
        StatSortSelection.PenaltyMinutes
      }
    }
    set(value) {
      val editor = userPrefs.edit()
      editor.putInt(SORT_SELECTION_KEY, value.index)
      editor.apply()
    }

  private val userPrefs: SharedPreferences
    get() = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
}
