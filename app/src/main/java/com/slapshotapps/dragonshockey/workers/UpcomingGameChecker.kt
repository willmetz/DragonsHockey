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

class UpcomingGameChecker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private var firebaseDatabase: FirebaseDatabase? = null

    override fun doWork(): Result {
        val userPrefsManager = UserPrefsManager(applicationContext)

        if (!userPrefsManager.notificationsEnabled) {
            return Result.success()
        }

        firebaseDatabase = FirebaseDatabase.getInstance()

        var schedule: SeasonSchedule? = null
        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribe({ seasonSchedule: SeasonSchedule? ->
                    schedule = seasonSchedule
                }, {
                    Timber.e("Error retrieving schedule")
                })

        val seasonSchedule = schedule ?: return Result.retry()

        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)
                ?: return Result.success()

        val today = Calendar.getInstance()
        val gameTime = Calendar.getInstance()
        gameTime.time = nextGame.gameTimeToDate()
        val notificationManager = NotificationManager(applicationContext)

        if (userPrefsManager.notificationsDaysBeforeGame == 0 && today.get(Calendar.DAY_OF_YEAR) == gameTime.get(Calendar.DAY_OF_YEAR)) {//schedule notification for today, with game today
            gameTime.add(Calendar.HOUR_OF_DAY, -2)
            notificationManager.scheduleGameNotification(gameTime.time, nextGame)
        } else if (today.get(Calendar.DAY_OF_YEAR) + 1 == gameTime.get(Calendar.DAY_OF_YEAR)) {////schedule notification for today with game being tomorrow
            notificationManager.scheduleGameNotification(gameTime.time, nextGame)
        }

        return Result.success()
    }
}