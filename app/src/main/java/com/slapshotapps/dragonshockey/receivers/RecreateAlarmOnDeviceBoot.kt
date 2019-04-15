package com.slapshotapps.dragonshockey.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.workers.UpcomingGameChecker
import timber.log.Timber
import java.util.concurrent.TimeUnit


class RecreateAlarmOnDeviceBoot: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Timber.d("Received broadcast")
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {

            Timber.d("Received on boot")

            val prefsManager = UserPrefsManager(context)

            if(!prefsManager.notificationsEnabled){
                return
            }

            Timber.d("notifications are enabled")
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

            val notificationWork = OneTimeWorkRequestBuilder<UpcomingGameChecker>()
                    .setConstraints(constraints)
                    .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 2, TimeUnit.MINUTES)
                    .build()

            val workManager = WorkManager.getInstance()
            workManager.enqueue(notificationWork)


        }
    }
}