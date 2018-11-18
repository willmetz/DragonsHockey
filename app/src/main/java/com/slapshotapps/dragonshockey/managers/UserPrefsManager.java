package com.slapshotapps.dragonshockey.managers;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection;

public class UserPrefsManager {

    private final String SHARED_PREFS_KEY = "HockeyPreferences";

    private final String SORT_SELECTION_KEY = "sortSelection";

    private final Context context;

    public UserPrefsManager(@NonNull Context context) {
        this.context = context;
    }

    public void saveStatSortPreference(StatSortSelection statSortSelection) {
        SharedPreferences.Editor editor = getUserPrefs().edit();
        editor.putInt(SORT_SELECTION_KEY, statSortSelection.getIndex());
        editor.apply();
    }

    public StatSortSelection getStatSortPreference() {
        final int sortIndex =
            getUserPrefs().getInt(SORT_SELECTION_KEY, StatSortSelection.Points.getIndex());

        if (StatSortSelection.GamesPlayed.getIndex() == sortIndex) {
            return StatSortSelection.GamesPlayed;
        } else if (StatSortSelection.Goals.getIndex() == sortIndex) {
            return StatSortSelection.Goals;
        } else if (StatSortSelection.Assists.getIndex() == sortIndex) {
            return StatSortSelection.Assists;
        } else if (StatSortSelection.Points.getIndex() == sortIndex) {
            return StatSortSelection.Points;
        } else {
            return StatSortSelection.PenaltyMinutes;
        }
    }

    private SharedPreferences getUserPrefs() {
        return context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    }
}
