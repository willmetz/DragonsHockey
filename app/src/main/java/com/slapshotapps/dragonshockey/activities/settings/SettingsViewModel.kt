package com.slapshotapps.dragonshockey.activities.settings

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.NotificationState
import com.slapshotapps.dragonshockey.managers.UserPrefsManager


class SettingsViewModel(private val userPrefsManager: UserPrefsManager,
                        private val listener: SettingsViewModelListener,
                        private val notificationManager: NotificationManager) {

    interface SettingsViewModelListener {
        fun onRequestNotificationsEnabled()
        fun onNotificationsDisabled()
        fun onEnableNotificationOptions()
    }


    fun getNotificationState(): NotificationState = userPrefsManager.notificationState


    fun onDisableNotifications(){
        listener.onNotificationsDisabled()
        notificationManager.cancelGameNotifications()
        userPrefsManager.notificationState = NotificationState.DISABLED
    }

    fun onRequestNotificationsEnabled(){
        listener.onRequestNotificationsEnabled()
    }

    fun onNotificationsEnabled(dayOfGame: Boolean){
        if(dayOfGame){
            userPrefsManager.notificationState = NotificationState.DAY_OF_GAME
        }else {
            userPrefsManager.notificationState = NotificationState.DAY_BEFORE_GAME
        }
    }
}