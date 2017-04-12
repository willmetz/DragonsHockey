package com.slapshotapps.dragonshockey.models;


import java.util.ArrayList;
import java.util.List;

public class SeasonStats {

    public List<GameStats> stats;

    public String seasonID;

    public SeasonStats(){
        stats = new ArrayList<>();
    }

    public SeasonStats(String seasonID){
        this();
        this.seasonID = seasonID;
    }

}
