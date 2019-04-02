package com.slapshotapps.dragonshockey.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.services.NotificationService
import timber.log.Timber
import java.util.*


const val ALARM_TOLERANCE_MINUTES = 5
const val NOTIFICATION_ALARM_REQUEST_CODE = 123

class NotificationManager(private val context: Context) {

    fun scheduleGameNotification(notificationTime: Date, game: Game) {
        Timber.d("Scheduling a notification")

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val windowStart = Calendar.getInstance()
        windowStart.time = notificationTime
        windowStart.add(Calendar.MINUTE, -1 * ALARM_TOLERANCE_MINUTES)

        val windowEnd = Calendar.getInstance()
        windowEnd.time = notificationTime
        windowEnd.add(Calendar.MINUTE, ALARM_TOLERANCE_MINUTES)

        alarmManager?.setWindow(
                AlarmManager.RTC_WAKEUP,
                windowStart.timeInMillis,
                windowEnd.timeInMillis,
                createGameNotificationIntent(game))
    }

    fun cancelGameNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(createGameNotificationIntent())
    }

    private fun createGameNotificationIntent(game: Game? = null): PendingIntent {
        val intent = NotificationService.createIntent(context, game)

        return PendingIntent.getService(context, NOTIFICATION_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}