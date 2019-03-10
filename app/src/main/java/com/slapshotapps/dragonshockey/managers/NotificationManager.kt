package com.slapshotapps.dragonshockey.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.slapshotapps.dragonshockey.services.NotificationService
import java.util.*


class NotificationManager(private val context: Context) {

    private val ALARM_TOLERANCE_MINUTES = 10

    fun scheduleFutureNotification(date: Date) {

        val intent = Intent(context, NotificationService::class.java)
        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val windowStart = Calendar.getInstance()
        windowStart.time = date
        windowStart.add(Calendar.MINUTE, -1 * ALARM_TOLERANCE_MINUTES)

        val windowEnd = Calendar.getInstance()
        windowEnd.time = date
        windowEnd.add(Calendar.MINUTE, ALARM_TOLERANCE_MINUTES)

        alarmManager?.setWindow(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                windowStart.timeInMillis,
                windowEnd.timeInMillis,
                pendingIntent)

    }
}