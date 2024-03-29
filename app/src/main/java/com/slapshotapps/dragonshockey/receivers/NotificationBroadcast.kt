package com.slapshotapps.dragonshockey.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.DateFormatter
import com.slapshotapps.dragonshockey.utils.SCHEDULE_CHANNEL_ID
import com.slapshotapps.dragonshockey.utils.areNotificationsEnabled
import com.slapshotapps.dragonshockey.utils.logAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.MainActivity
import com.slapshotapps.dragonshockey.models.Game
import timber.log.Timber
import java.util.*

const val GAME_NOTIFICATION_ID = 2345

const val GAME_TIME_EXTRA = "GAME_TIME_EXTRA"
const val GAME_OPPONENT_EXTRA = "GAME_OPPONENT_EXTRA"
const val GAME_HOME_EXTRA = "HOME_GAME_EXTRA"


class NotificationBroadcast : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        if(areNotificationsEnabled(context).not()) return

        val gameTime = intent.getLongExtra(GAME_TIME_EXTRA, 0L)
        val homeGame = intent.getBooleanExtra(GAME_HOME_EXTRA, false)
        val opponent = intent.getStringExtra(GAME_OPPONENT_EXTRA)

        if (opponent != null) {
            val notificationTitle = "Dragons Hockey Game"
            val gameTimeAsDate = Date(gameTime)

            val notificationText = if (DateUtils.isToday(gameTime)) {

                context.getString(R.string.today_gameday_notification, DateFormatter.getGameTime(gameTimeAsDate),
                        if (homeGame) "Home" else "Guest", opponent)
            } else {
                context.getString(R.string.gameday_notification, DateFormatter.getGameDateTime(gameTimeAsDate),
                        if (homeGame) "Home" else "Guest", opponent)
            }

            //Timber.d("Notification Broadcast, would send notification here")
            displayNotification(notificationTitle, notificationText, context)

            logAnalyticEvent(HockeyAnalyticEvent.NOTIFICATION_TRIGGERED, FirebaseAnalytics.getInstance(context))
        } else {
            Timber.e("Notification Broadcast game NOT valid")
        }
    }


    private fun displayNotification(title: String, description: String, context: Context) {


        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, SCHEDULE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_dragon_eye_red))
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()


        with(NotificationManagerCompat.from(context)) {
            notify(GAME_NOTIFICATION_ID, notification)
        }
    }


    companion object {
        fun createIntent(context: Context, game: Game?): Intent {
            val intent = Intent(context, NotificationBroadcast::class.java)

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