package com.slapshotapps.dragonshockey.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class UpcomingGameChecker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val userPrefsManager = UserPrefsManager(applicationContext)

        if (!userPrefsManager.notificationsEnabled) {
            return Result.success()
        }

        var schedule: SeasonSchedule? = null
        ScheduleObserver.getHockeySchedule(FirebaseDatabase.getInstance())
                .subscribe({ seasonSchedule: SeasonSchedule? ->
                    schedule = seasonSchedule
                }, {
                    Timber.e("Error retrieving schedule")
                })

        var waitCount = 50
        while (schedule == null && waitCount-- > 0) {
            try {
                Thread.sleep(200)
            } catch (e: InterruptedException) {
                //no-op
            }
        }

        val seasonSchedule = schedule ?: return Result.failure()

        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)
                ?: return Result.success()

        val gameTime = Calendar.getInstance()
        gameTime.time = nextGame.gameTimeToDate()
        val notificationManager = NotificationManager(applicationContext)

        if (userPrefsManager.notificationsDaysBeforeGame == 0) {//schedule notification for day of game
            val notificationTime = gameTime.timeInMillis - TimeUnit.MINUTES.toMillis(90)
            val notificationDate = Date(notificationTime)

            if (Date().before(notificationDate)) {
                notificationManager.scheduleGameNotification(notificationDate, nextGame)
            }
        } else {
            //schedule notification for the day before the game at 6pm
            gameTime.add(Calendar.DAY_OF_YEAR, -1)
            gameTime.set(Calendar.HOUR_OF_DAY, 18)
            gameTime.set(Calendar.MINUTE, 0)
            gameTime.set(Calendar.SECOND, 0)
            if (Date().before(gameTime.time)) {
                notificationManager.scheduleGameNotification(gameTime.time, nextGame)
            }
        }

        return Result.success()
    }
}