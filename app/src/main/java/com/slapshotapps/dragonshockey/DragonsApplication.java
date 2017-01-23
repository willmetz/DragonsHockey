package com.slapshotapps.dragonshockey;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by willmetz on 5/30/16.
 */

public class DragonsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
