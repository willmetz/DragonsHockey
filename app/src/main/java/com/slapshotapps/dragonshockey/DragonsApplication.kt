package com.slapshotapps.dragonshockey

import android.app.Application
import com.slapshotapps.dragonshockey.utils.NotificationUtil
import timber.log.Timber


/**
 * The [Application] class for the app.
 */
class DragonsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        NotificationUtil.createNotificationChannel(this)
    }


}
