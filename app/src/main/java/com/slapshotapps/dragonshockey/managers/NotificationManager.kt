package com.slapshotapps.dragonshockey.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.slapshotapps.dragonshockey.services.NotificationService
import java.util.*


const val ALARM_TOLERANCE_MINUTES = 1
const val NOTIFICATION_ALARM_REQUEST_CODE = 123

class NotificationManager(private val context: Context) {

    fun scheduleFutureNotification(date: Date) {

        val testTime = Calendar.getInstance()
        testTime.add(Calendar.MINUTE, 1)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val windowStart = Calendar.getInstance()
        windowStart.time = testTime.time
        windowStart.add(Calendar.MINUTE, -1 * ALARM_TOLERANCE_MINUTES)

        val windowEnd = Calendar.getInstance()
        windowEnd.time = testTime.time
        windowEnd.add(Calendar.MINUTE, ALARM_TOLERANCE_MINUTES)

        alarmManager?.setWindow(
                AlarmManager.RTC_WAKEUP,
                windowStart.timeInMillis,
                windowEnd.timeInMillis,
                createAlarmIntent())
    }

    fun cancelNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(createAlarmIntent())
    }

    private fun createAlarmIntent(): PendingIntent {
        val intent = Intent(context, NotificationService::class.java)
        return PendingIntent.getService(context, NOTIFICATION_ALARM_REQUEST_CODE, intent, 0)
    }
}