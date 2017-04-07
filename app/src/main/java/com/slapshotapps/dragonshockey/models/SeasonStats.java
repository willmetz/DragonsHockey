package com.slapshotapps.dragonshockey.models;


import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class SeasonStats {

    @PropertyName("games")
    public List<GameStats> stats;

    public String seasonID;

    public SeasonStats(){
        stats = new ArrayList<>();
    }
}
