package com.slapshotapps.dragonshockey.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.SCHEDULE_CHANNEL_ID
import com.slapshotapps.dragonshockey.Utils.DateFormatter
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.models.Game
import java.util.*

const val SCHEDULE_NOTIFICATION_ID = 1234
const val GAME_NOTIFICATION_ID = 2345

const val GAME_TIME_EXTRA = "GAME_TIME_EXTRA"
const val GAME_OPPONENT_EXTRA = "GAME_OPPONENT_EXTRA"
const val GAME_HOME_EXTRA = "HOME_GAME_EXTRA"

class NotificationService : IntentService("HockeyNotificationService") {

    protected var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notification = NotificationCompat.Builder(applicationContext, SCHEDULE_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_dragon_eye_white)
                    .setContentText(getString(R.string.schedule_notification_channel))
                    .build()

            startForeground(SCHEDULE_NOTIFICATION_ID, notification)
        }
    }

    override fun onHandleIntent(intent: Intent?) {

        firebaseDatabase = FirebaseDatabase.getInstance()

        val gameTime = intent?.getLongExtra(GAME_TIME_EXTRA, 0L)
        val homeGame = intent?.getBooleanExtra(GAME_HOME_EXTRA, false)
        val opponent = intent?.getStringExtra(GAME_OPPONENT_EXTRA)

        if (gameTime != null && homeGame != null && opponent != null) {
            val notificationTitle = "Dragons Hockey Game"
            val gameTimeAsDate = Date(gameTime)

            val notificationText = if (DateUtils.isToday(gameTime)) {

                applicationContext.getString(R.string.today_gameday, DateFormatter.getGameTime(gameTimeAsDate),
                        if (homeGame) "Home" else "Guest", opponent)
            } else {
                applicationContext.getString(R.string.gameday, DateFormatter.getGameDateTime(gameTimeAsDate),
                        if (homeGame) "Home" else "Guest", opponent)
            }

            displayNotification(notificationTitle, notificationText)
        }
    }

    private fun displayNotification(title: String, description: String) {


        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, SCHEDULE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_dragon_eye_red))
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
        fun createIntent(context: Context, game: Game?): Intent {
            val intent = Intent(context, NotificationService::class.java)

            //as pending intents cannot reliably contain parcelable data, just put the needed fields as extras
            //refer to this for more details: https://issuetracker.google.com/issues/37097877
            val gameTime = game?.gameTimeToDate()
            val opponent = game?.opponent
            val home = game?.home == true

            if (gameTime != null && opponent != null) {
                intent.putExtra(GAME_TIME_EXTRA, gameTime.time)
                intent.putExtra(GAME_OPPONENT_EXTRA, opponent)
                intent.putExtra(GAME_HOME_EXTRA, home)
            }

            return intent
        }
    }

}