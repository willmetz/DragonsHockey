package com.slapshotapps.dragonshockey.models;

import android.support.annotation.Keep;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import java.util.List;

@Keep
public class GameStats {

    @Exclude
    public String key;

    public int gameID;

    @PropertyName("stats")
    public List<Stats> gameStats;

    @Keep
    public static class Stats {

        public int playerID;
        public int assists;
        public int goals;
        public boolean present;
        public int penaltyMinutes;
        public int goalsAgainst;

        public Stats() {

        }

        public Stats(int playerID, int assists, int goals, int penaltyMinutes, boolean present, int goalsAgainst) {
            this.playerID = playerID;
            this.assists = assists;
            this.present = present;
            this.goals = goals;
            this.penaltyMinutes = penaltyMinutes;
            this.goalsAgainst = goalsAgainst;
        }
    }

    public Stats getPlayerStats(int playerID) {
        if (gameStats != null) {
            for (Stats playerStats : gameStats) {
                if (playerStats.playerID == playerID) {
                    return playerStats;
                }
            }
        }

        return new Stats();
    }
}
