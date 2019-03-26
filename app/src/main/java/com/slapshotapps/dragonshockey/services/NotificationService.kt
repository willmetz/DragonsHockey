package com.slapshotapps.dragonshockey.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.SCHEDULE_CHANNEL_ID
import com.slapshotapps.dragonshockey.Utils.DateFormatter
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import java.util.*

const val SCHEDULE_NOTIFICATION_ID = 1234

class NotificationService : IntentService("HockeyNotificationService") {

    protected var firebaseDatabase: FirebaseDatabase? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val notification = NotificationCompat.Builder(applicationContext, SCHEDULE_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_dragon_eye_white)
                    .setContentText(getString(R.string.schedule_notification_channel))
                    .build()


            startForeground(SCHEDULE_NOTIFICATION_ID, notification)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {

        firebaseDatabase = FirebaseDatabase.getInstance()

        val prefManager = UserPrefsManager(applicationContext)

        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribe({
                    val now = Date()
                    val gameForNotification = ScheduleUtils.getGameAfterDate(now, it.allGames)

                    if(gameForNotification == null){
                        return@subscribe
                    }

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

//                    if(prefManager.notificationsEnabled){
//                        val nextGameForNotification = ScheduleUtils.getGameAfterDate(gameForNotification.gameTimeToDate(), it.allGames)
//
//                        val notificationManager = NotificationManager(applicationContext)
//
//                        val calendar = Calendar.getInstance()
//                        calendar.time = nextGameForNotification.gameTimeToDate()
//
//                        notificationManager.scheduleFutureNotification(calendar)
//                    }

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
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(SCHEDULE_NOTIFICATION_ID, notification)
        }
    }

}