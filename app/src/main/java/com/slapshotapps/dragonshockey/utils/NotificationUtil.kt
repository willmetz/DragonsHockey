package com.slapshotapps.dragonshockey.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.slapshotapps.dragonshockey.R

const val SCHEDULE_CHANNEL_ID = "ScheduleChannel"

class NotificationUtil {
    companion object {
        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && areNotificationsEnabled(context)) {
                val name = context.getString(R.string.schedule_notification_channel)
                val descriptionText = context.getString(R.string.schedule_notification_channel_desc)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(SCHEDULE_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}