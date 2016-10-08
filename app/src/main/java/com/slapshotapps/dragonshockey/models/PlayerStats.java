package com.slapshotapps.dragonshockey.models;

import java.util.Comparator;

/**
 * Created by willmetz on 9/5/16.
 */

public class PlayerStats implements Comparable<PlayerStats>{

    public int playerID;
    public String firstName;
    public String lastName;
    public int goals;
    public int assists;
    public int gamesPlayed;
    public int points;

    public PlayerStats ( int playerID, String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerID = playerID;
    }

    @Override
    public int compareTo(PlayerStats playerStats) {

        if(points < playerStats.points){
            return 1;
        }else if( points > playerStats.points){
            return -1;
        }
        return lastName.compareTo(playerStats.lastName);
    }
}
