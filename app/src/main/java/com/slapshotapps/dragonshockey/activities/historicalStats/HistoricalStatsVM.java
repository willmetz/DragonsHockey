package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

import java.util.List;

import timber.log.Timber;

public class HistoricalStatsVM {

    private final PlayerHistoricalStats playerStats;

    public HistoricalStatsVM(PlayerHistoricalStats playerStats){
        this.playerStats = playerStats;
    }

    public int getGamesPlayed(){
        int gamesPlayed = 0;

        if(playerStats != null && playerStats.games !=null){

            for (GameStats gameStats: playerStats.games) {
                if(gameStats.gameStats.size() == 1){
                    gamesPlayed += gameStats.gameStats.get(0).present ? 1 : 0;
                }else{
                    Timber.e("More than one stat is associated with a player");
                }
            }
        }

        return gamesPlayed;
    }

    public String getSeasonName(){
        return playerStats.seasonID;
    }

    public int getGoals(){
        int goals = 0;

        if(playerStats != null && playerStats.games !=null){

            for (GameStats gameStats: playerStats.games) {
                if(gameStats.gameStats.size() == 1){
                    goals += gameStats.gameStats.get(0).goals;
                }else{
                    Timber.e("More than one stat is associated with a player");
                }
            }
        }

        return goals;
    }

    public int getAssists(){
        int assists = 0;

        if(playerStats != null && playerStats.games !=null){

            for (GameStats gameStats: playerStats.games) {
                if(gameStats.gameStats.size() == 1){
                    assists += gameStats.gameStats.get(0).assists;
                }else{
                    Timber.e("More than one stat is associated with a player");
                }
            }
        }

        return assists;
    }

    public int getPoints(){
        return getGoals() + getAssists();
    }

}
