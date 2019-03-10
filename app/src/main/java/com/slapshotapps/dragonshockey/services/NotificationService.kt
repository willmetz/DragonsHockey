package com.slapshotapps.dragonshockey.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.SCHEDULE_CHANNEL_ID
import com.slapshotapps.dragonshockey.Utils.DateFormatter
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import timber.log.Timber
import java.util.*

const val SCHEDULE_NOTIFICATION_ID = 1234

class NotificationService : IntentService("HockeyNotificationService") {

    protected var firebaseDatabase: FirebaseDatabase? = null

    override fun onHandleIntent(intent: Intent?) {

        firebaseDatabase = FirebaseDatabase.getInstance()

        try {
            firebaseDatabase!!.setPersistenceEnabled(true)
        } catch (exception: DatabaseException) {
            Timber.e("Unable to set persistance for Firebase")
        }

        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribe({
                    val now = Date()
                    val gameForNotification = ScheduleUtils.getGameAfterDate(now, it.allGames)

                    val notificationTitle = "Dragons Hockey Game"
                    val gameTime = if (gameForNotification.gameTimeToDate() != null) gameForNotification.gameTimeToDate() else Date()


                    val notificationText = if (DateUtils.isToday(gameTime!!.time)) {

                        applicationContext.getString(R.string.today_gameday, DateFormatter.getGameTime(gameTime),
                                if (gameForNotification.home) "Home" else "Guest")
                    } else {
                        applicationContext.getString(R.string.gameday, DateFormatter.getGameDateTime(gameTime),
                                if (gameForNotification.home) "Home" else "Guest")
                    }

                    displayNotification(notificationTitle, notificationText)


                }, {
                    //no-op here
                })

    }

    private fun displayNotification(title: String, description: String) {


        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, SCHEDULE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setContentTitle(title)
                .setContentInfo(description)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(SCHEDULE_NOTIFICATION_ID, notification)
        }
    }

}