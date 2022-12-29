package com.slapshotapps.dragonshockey.workers

import android.content.Context
import androidx.work.*
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.utils.ScheduleUtils
import com.slapshotapps.dragonshockey.managers.NotificationState
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class UpcomingGameChecker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val userPrefsManager = UserPrefsManager(applicationContext)

        val notificationState = userPrefsManager.notificationState
        if (notificationState == NotificationState.DISABLED) {
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
            kotlin.runCatching { Thread.sleep(200) }
        }

        val seasonSchedule = schedule ?: return Result.failure()

        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)
                ?: return Result.success()


        nextGame.gameTimeToDate()?.let { nextGameTime ->
            val gameTime = Calendar.getInstance()
            gameTime.time = nextGameTime

            if (notificationState == NotificationState.DAY_OF_GAME) {//schedule notification for day of game
                val notificationTime = gameTime.timeInMillis - TimeUnit.MINUTES.toMillis(90)
                val notificationDate = Date(notificationTime)

                if (Date().before(notificationDate)) {
                    createNotificationDisplayWorker(nextGame, notificationDate)
                }
            } else {
                //schedule notification for the day before the game at 6pm
                gameTime.add(Calendar.DAY_OF_YEAR, -1)
                gameTime.set(Calendar.HOUR_OF_DAY, 18)
                gameTime.set(Calendar.MINUTE, 0)
                gameTime.set(Calendar.SECOND, 0)
                if (Date().before(gameTime.time)) {
                    createNotificationDisplayWorker(nextGame, gameTime.time)
                }
            }
        }


        return Result.success()
    }

    private fun createNotificationDisplayWorker(game: Game, notificationTime: Date) {

        val inputData = workDataOf(
                NotificationDisplayWorker.GAME_ALARM_TIME to notificationTime.time,
                NotificationDisplayWorker.GAME_HOME_KEY to game.home,
                NotificationDisplayWorker.GAME_TIME_KEY to (game.gameTimeToDate()?.time ?: 0L),
                NotificationDisplayWorker.GAME_OPPONENT_KEY to (game.opponent ?: "")
        )

        val initialDelayInMs = notificationTime.time - Date().time

        val request = OneTimeWorkRequestBuilder<NotificationDisplayWorker>()
                .setInputData(inputData)
                .setInitialDelay(initialDelayInMs, TimeUnit.MILLISECONDS)
                .addTag(DISPLAY_WORKER_ID)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                .build()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniqueWork(DISPLAY_NOTIFICATION_WORK_JOB,
                ExistingWorkPolicy.REPLACE,
                request)
    }

    companion object {
        const val DISPLAY_WORKER_ID = "DisplayNotificationTask"
        const val DISPLAY_NOTIFICATION_WORK_JOB = "DisplayNotificationWorkJob"
    }
}