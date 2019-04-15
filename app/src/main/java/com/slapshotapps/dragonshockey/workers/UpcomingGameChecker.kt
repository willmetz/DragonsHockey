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

        Timber.d("Workmanager work started")
        val userPrefsManager = UserPrefsManager(applicationContext)

        if (!userPrefsManager.notificationsEnabled) {
            return Result.success()
        }

        Timber.d("Workmanager work notifications enabled")

        firebaseDatabase = FirebaseDatabase.getInstance()

        var schedule: SeasonSchedule? = null
        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribe({ seasonSchedule: SeasonSchedule? ->
                    schedule = seasonSchedule
                }, {
                    Timber.e("Error retrieving schedule")
                })

        var waitCount = 40
        while (schedule == null && waitCount-- > 0) {
            try {
                Thread.sleep(200)
            } catch (e: InterruptedException) {
                //no-op
            }
        }

        Timber.d("Workmanager done waiting for schedule")

        val seasonSchedule = schedule ?: return Result.failure()

        Timber.d("Workmanager schedule is not null")

        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)
                ?: return Result.success()

        val gameTime = Calendar.getInstance()
        gameTime.time = nextGame.gameTimeToDate()
        val notificationManager = NotificationManager(applicationContext)

        if (userPrefsManager.notificationsDaysBeforeGame == 0) {//schedule notification for day of game
            Timber.d("Workmanager scheduling a game day notification")
            gameTime.add(Calendar.HOUR_OF_DAY, -1)
            if (Date().before(gameTime.time)) {
                notificationManager.scheduleGameNotification(gameTime.time, nextGame)
            }
        } else {
            Timber.d("Workmanager scheduling a day before game day notification")
            //schedule notification for the day before the game
            gameTime.add(Calendar.DAY_OF_YEAR, -1)
            notificationManager.scheduleGameNotification(gameTime.time, nextGame)
        }

        return Result.success()
    }
}