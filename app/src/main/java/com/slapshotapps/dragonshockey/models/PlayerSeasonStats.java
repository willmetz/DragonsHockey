package com.slapshotapps.dragonshockey.models;


public class PlayerSeasonStats {

    public String seasonID;
    public int goals;
    public int assists;
    public int gamesPlayed;


    public PlayerSeasonStats(String seasonID){
        this.seasonID = seasonID;
    }

    public int getPoints(){
        return goals + assists;
    }
}
