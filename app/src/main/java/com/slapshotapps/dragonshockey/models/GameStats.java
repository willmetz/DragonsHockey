package com.slapshotapps.dragonshockey.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by willmetz on 9/5/16.
 */

public class GameStats {

    @Exclude
    public String key;

    public int gameID;

    @PropertyName("stats")
    public List<Stats> gameStats;

    public static class Stats{

        public int playerID;
        public int assists;
        public int goals;
        public boolean present;

    }

    public Stats getPlayerStats(int playerID){
        if(gameStats != null) {
            for (Stats playerStats : gameStats) {
                if (playerStats.playerID == playerID) {
                    return playerStats;
                }
            }
        }

        return new Stats();
    }



}
