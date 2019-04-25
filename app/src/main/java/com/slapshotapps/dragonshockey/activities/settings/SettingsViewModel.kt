package com.slapshotapps.dragonshockey.activities.settings

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.UserPrefsManager


class SettingsViewModel(private val userPrefsManager: UserPrefsManager, private val listener: SettingsViewModelListener, private val notificationManager: NotificationManager) : LifecycleObserver {

    interface SettingsViewModelListener {
        fun onEnableNotifications()
        fun onDisableNotifications()
    }

    val dayOfGameSelected = ObservableBoolean(false)
    val dayBeforeGameSelected = ObservableBoolean(false)


    val notificationsEnabled = ObservableBoolean(false)

    init {
        dayOfGameSelected.set(userPrefsManager.notificationsDaysBeforeGame == 0)
        dayBeforeGameSelected.set(userPrefsManager.notificationsDaysBeforeGame == 1)
        notificationsEnabled.set(userPrefsManager.notificationsEnabled)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onSaveData() {
        val notificationStateOnUI = notificationsEnabled.get()

        val notificationSetupChanged = notificationStateOnUI != userPrefsManager.notificationsEnabled ||
                (dayOfGameSelected.get() && userPrefsManager.notificationsDaysBeforeGame != 0) ||
                (!dayOfGameSelected.get() && userPrefsManager.notificationsDaysBeforeGame == 0)

        if (notificationSetupChanged) {
            userPrefsManager.notificationsDaysBeforeGame = if (dayOfGameSelected.get()) 0 else 1
            userPrefsManager.notificationsEnabled = notificationStateOnUI

            if (notificationStateOnUI) {
                listener.onEnableNotifications()
            } else if (!notificationStateOnUI) {
                listener.onDisableNotifications()
                notificationManager.cancelGameNotifications()
            }
        }

    }
}