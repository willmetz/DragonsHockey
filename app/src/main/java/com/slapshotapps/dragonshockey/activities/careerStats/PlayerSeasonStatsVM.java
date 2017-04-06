package com.slapshotapps.dragonshockey.activities.careerStats;


public class PlayerSeasonStatsVM {

    public String seasonID;
    public int goals;
    public int assists;
    public int gamesPlayed;


    public PlayerSeasonStatsVM(String seasonID){
        this.seasonID = seasonID;
    }

    public String getPoints(){
        return String.valueOf(goals + assists);
    }

    public String getGoals(){
        return String.valueOf(goals);
    }

    public String getAssists(){
        return String.valueOf(assists);
    }

    public String getGamesPlayed(){
        return String.valueOf(gamesPlayed);
    }
}
