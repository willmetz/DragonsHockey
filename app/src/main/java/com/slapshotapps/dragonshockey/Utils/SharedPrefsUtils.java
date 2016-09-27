package com.slapshotapps.dragonshockey.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.slapshotapps.dragonshockey.Config;

/**
 * Created by willmetz on 9/25/16.
 */

public class SharedPrefsUtils {

    private static final String LOGGED_IN = "com.slapshotapps.dragonshockey.isLoggedIn";


    public static boolean isUserLoggedIn(Context context){

        SharedPreferences sharedPreferences = getAppSharedPrefs(context);

        return sharedPreferences.getBoolean(LOGGED_IN, false);
    }

    private static SharedPreferences getAppSharedPrefs(Context context){
        return context.getSharedPreferences(Config.MAIN_PREFS, Context.MODE_PRIVATE);
    }
}
