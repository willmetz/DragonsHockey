package com.slapshotapps.dragonshockey.models;

import java.util.List;

/**
 * Created by willmetz on 9/5/16.
 */

public class GameStats {

    public int gameID;

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
