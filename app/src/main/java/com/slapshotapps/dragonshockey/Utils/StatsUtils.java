package com.slapshotapps.dragonshockey.Utils;

import android.util.SparseArray;

import com.slapshotapps.dragonshockey.models.PlayerStats;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Created by willmetz on 9/12/16.
 */

public class StatsUtils {

    public static ArrayList<PlayerStats> toPlayerStats(@NonNull SparseArray<PlayerStats> sparseArray) {

        ArrayList<PlayerStats> statsArrayList = new ArrayList<>();

        for (int i = 0; i < sparseArray.size(); i++) {
            statsArrayList.add(sparseArray.valueAt(i));
        }

        return statsArrayList;
    }

    public static String fullPlayerName(@NonNull PlayerStats playerStats) {

        String name = playerStats.getFirstName() != null ? playerStats.getFirstName() : "";

        name += playerStats.getLastName() != null ? " " + playerStats.getLastName() : "";

        return name;
    }
}
