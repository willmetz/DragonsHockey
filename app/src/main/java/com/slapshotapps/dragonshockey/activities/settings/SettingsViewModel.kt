package com.slapshotapps.dragonshockey.activities.settings

import android.widget.TimePicker
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import java.util.*


class SettingsViewModel(private val userPrefsManager: UserPrefsManager, private val listener: SettingsViewModelListener,
                        private val seasonSchedule: SeasonSchedule, private val notificationManager: NotificationManager) : TimePicker.OnTimeChangedListener, LifecycleObserver {

    interface SettingsViewModelListener{
        fun onShowNotification()
    }

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


        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)
        val gameTime = nextGame?.gameTimeToDate()



        if(userPrefsManager.notificationsEnabled){// && gameTime != null) {
            val notificationTime = Calendar.getInstance()

            notificationTime.add(Calendar.MINUTE, 2)

//            notificationTime.time = gameTime
//            val hourOfDay = userPrefsManager.notificationsHourOfDayMilitaryTime/100
//            val minuteOfDay = userPrefsManager.notificationsHourOfDayMilitaryTime - hourOfDay
//            notificationTime.set(Calendar.HOUR_OF_DAY,userPrefsManager.notificationsHourOfDayMilitaryTime/100)
//            notificationTime.set(Calendar.MINUTE, minuteOfDay)
//            notificationTime.set(Calendar.SECOND, 0)
//            notificationTime.set(Calendar.MILLISECOND, 0)
//
//            notificationTime.add(Calendar.DAY_OF_YEAR, -1 * userPrefsManager.notificationsDaysBeforeGame)
            notificationManager.scheduleFutureNotification(notificationTime.time)
        }else if(!userPrefsManager.notificationsEnabled){
            notificationManager.cancelNotifications()
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        userPrefsManager.notificationsHourOfDayMilitaryTime = hourOfDay * 100 + minute
    }
}