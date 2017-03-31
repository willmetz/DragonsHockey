package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

public class HistoricalStatsRowVM {

    private final PlayerHistoricalStats playerStats;

    public HistoricalStatsRowVM(PlayerHistoricalStats playerStats) {
        this.playerStats = playerStats;
    }

    public int getGamesPlayed() {
        int gamesPlayed = 0;

        if (playerStats != null && playerStats.gamesStats != null) {
            for (GameStats.Stats gameStats : playerStats.gamesStats) {
                gamesPlayed += gameStats.present ? 1 : 0;
            }
        }

        return gamesPlayed;
    }

    public String getSeasonName() {
        if (playerStats.seasonID == null) {
            return "";
        }
        return playerStats.seasonID;
    }

    public int getGoals() {
        int goals = 0;

        if (playerStats != null && playerStats.gamesStats != null) {
            for (GameStats.Stats gameStats : playerStats.gamesStats) {
                goals += gameStats.goals;
            }
        }

        return goals;
    }

    public int getAssists() {
        int assists = 0;

        if (playerStats != null && playerStats.gamesStats != null) {
            for (GameStats.Stats gameStats : playerStats.gamesStats) {
                assists += gameStats.assists;
            }
        }

        return assists;
    }

    public int getPoints() {
        return getGoals() + getAssists();
    }

}
