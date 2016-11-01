package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.PlayerStats;

/**
 * Created on 10/31/16.
 */

public class PlayerStatsViewModel {

    private String playerName;
    private int goals, assists, playerID, playerNumber;
    private boolean isPresent;

    public PlayerStatsViewModel(String playerName, int goals, int assists,
                                int playerID, int playerNumber, boolean isPresent){
        this.playerID = playerID;
        this.assists = assists;
        this.goals = goals;
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.isPresent = isPresent;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    public boolean getPresence(){
        return isPresent;
    }

    public void setPresence(boolean isPresent){
        this.isPresent = isPresent;
    }

    public String getGoals(){
        return String.valueOf(goals);
    }

    public void setGoals(String goals){
        if(goals == null || goals.isEmpty()){
            this.goals = 0;
        }else{
            this.goals = Integer.valueOf(goals);
        }
    }

    public String getAssists(){
        return String.valueOf(assists);
    }

    public void setAssists(String assists){
        if(assists == null || assists.isEmpty()){
            this.assists = 0;
        }else{
            this.assists = Integer.valueOf(assists);
        }
    }

}
