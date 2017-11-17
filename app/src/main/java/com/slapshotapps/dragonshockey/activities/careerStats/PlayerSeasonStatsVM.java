package com.slapshotapps.dragonshockey.activities.careerStats;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.PlayerStats;

public class PlayerSeasonStatsVM {

    public String seasonID;
    public int goals;
    public int assists;
    public int gamesPlayed;
    public int penaltyMinutes;

    public PlayerSeasonStatsVM(String seasonID) {
        this.seasonID = seasonID;
    }

    public PlayerSeasonStatsVM(PlayerStats playerStats, String seasonID) {
        goals = playerStats.goals;
        assists = playerStats.assists;
        gamesPlayed = playerStats.gamesPlayed;
        penaltyMinutes = playerStats.penaltyMinutes;
        this.seasonID = seasonID;
    }

    public String getPoints() {
        return String.valueOf(goals + assists);
    }

    public String getGoals() {
        return String.valueOf(goals);
    }

    public String getAssists() {
        return String.valueOf(assists);
    }

    public String getGamesPlayed() {
        return String.valueOf(gamesPlayed);
    }

    public String getPenaltyMinutes() {
        return String.valueOf(penaltyMinutes);
    }

    public int getBackgroundColor(Context context) {
        return seasonID.equalsIgnoreCase("career") ? ContextCompat.getColor(context,
            R.color.lightGray) : ContextCompat.getColor(context, R.color.standardBackground);
    }
}
