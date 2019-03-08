package com.slapshotapps.dragonshockey.activities.settings

import android.widget.TimePicker
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.slapshotapps.dragonshockey.managers.UserPrefsManager


class SettingsViewModel(
    private val userPrefsManager: UserPrefsManager) : TimePicker.OnTimeChangedListener, LifecycleObserver {

  val dayOfGameSelected = ObservableBoolean(false)
  val dayBeforeGameSelected = ObservableBoolean(false)


  val notificationsEnabled = ObservableBoolean(false)

  init {
    dayOfGameSelected.set(userPrefsManager.notificationsDaysBeforeGame == 0)
    dayBeforeGameSelected.set(userPrefsManager.notificationsDaysBeforeGame == 1)
    notificationsEnabled.set(userPrefsManager.notificationsEnabled)
  }

  fun getDesiredNotificationTime(): Int {
    return userPrefsManager.notificationsHourOfDayMilitaryTime
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onSaveData() {
    userPrefsManager.notificationsEnabled = notificationsEnabled.get()
    userPrefsManager.notificationsDaysBeforeGame = if (dayOfGameSelected.get()) 0 else 1
  }

  override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
    userPrefsManager.notificationsHourOfDayMilitaryTime = hourOfDay * 100 + minute
  }
}