package com.slapshotapps.dragonshockey.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.DateFormatter
import com.slapshotapps.dragonshockey.utils.SCHEDULE_CHANNEL_ID
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.receivers.GAME_NOTIFICATION_ID
import java.util.*


class NotificationDisplayWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val gameTimeInMilliseconds = inputData.getLong(GAME_TIME_KEY, 0)
        val opponent = inputData.getString(GAME_OPPONENT_KEY)
        val isHomeGame = inputData.getBoolean(GAME_HOME_KEY, false)

        if (gameTimeInMilliseconds > 0 && opponent != null) {

            val notificationTitle = applicationContext.getString(R.string.notification_title)
            val gameTimeAsDate = Date(gameTimeInMilliseconds)

            val notificationText = if (DateUtils.isToday(gameTimeInMilliseconds)) {

                applicationContext.getString(R.string.today_gameday_notification, DateFormatter.getGameTime(gameTimeAsDate),
                        if (isHomeGame) "Home" else "Guest", opponent)
            } else {
                applicationContext.getString(R.string.gameday_notification, DateFormatter.getGameDateTime(gameTimeAsDate),
                        if (isHomeGame) "Home" else "Guest", opponent)
            }

            displayNotification(notificationTitle, notificationText)
        }

        return Result.success()
    }

    private fun displayNotification(title: String, description: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, SCHEDULE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_dragon_eye_red))
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(GAME_NOTIFICATION_ID, notification)
        }
    }

    companion object {
        const val GAME_TIME_KEY = "gameTime"
        const val GAME_OPPONENT_KEY = "opponent"
        const val GAME_HOME_KEY = "homeGame"
        const val GAME_ALARM_TIME = "gameAlarmTime"
    }
}