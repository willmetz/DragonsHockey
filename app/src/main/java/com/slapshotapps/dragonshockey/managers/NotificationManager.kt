package com.slapshotapps.dragonshockey.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.receivers.NotificationBroadcast
import java.util.*
import java.util.concurrent.TimeUnit


const val ALARM_TOLERANCE_MINUTES = 5L
const val NOTIFICATION_ALARM_REQUEST_CODE = 123

class NotificationManager(private val context: Context) {

    fun scheduleGameNotification(notificationTime: Date, game: Game) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        alarmManager?.setWindow(
                AlarmManager.RTC_WAKEUP,
                notificationTime.time,
                TimeUnit.MINUTES.toMillis(ALARM_TOLERANCE_MINUTES),
                createGameNotificationIntent(game))
    }

    fun cancelGameNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(createGameNotificationIntent())
    }

    private fun createGameNotificationIntent(game: Game? = null): PendingIntent {
        val intent = NotificationBroadcast.createIntent(context, game)

        //need to use a broadcast service here as android 8 has restrictions on services started while the app is in the background
        return PendingIntent.getBroadcast(context, NOTIFICATION_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}