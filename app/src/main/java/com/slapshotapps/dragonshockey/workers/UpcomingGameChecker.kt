package com.slapshotapps.dragonshockey.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import java.util.*

class UpcomingGameChecker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private var firebaseDatabase: FirebaseDatabase? = null

    override fun doWork(): Result {
        val userPrefsManager = UserPrefsManager(applicationContext)

        if(!userPrefsManager.notificationsEnabled){
            return Result.success()
        }

        firebaseDatabase = FirebaseDatabase.getInstance()

        var schedule : SeasonSchedule ?= null
        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribe({seasonSchedule: SeasonSchedule? ->
                    schedule = seasonSchedule
                }, {

                })

        val seasonSchedule = schedule

        if(seasonSchedule == null){
            return Result.retry()
        }

        val nextGame = ScheduleUtils.getGameAfterDate(Date(), seasonSchedule.allGames)

        if(nextGame == null){
            return Result.success()
        }

        if(userPrefsManager.notificationsDaysBeforeGame == 0){
            val today = Calendar.getInstance()
            val gameTime = Calendar.getInstance()
            gameTime.time = nextGame.gameTimeToDate()

            if(today.get(Calendar.DAY_OF_YEAR) == gameTime.get(Calendar.DAY_OF_YEAR)){
                //schedule notification for today
            }
        }

        return Result.success()
    }
}