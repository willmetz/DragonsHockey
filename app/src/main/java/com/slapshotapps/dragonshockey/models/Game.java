package com.slapshotapps.dragonshockey.models;

import com.slapshotapps.dragonshockey.Utils.DateFormaters;

import java.util.Date;

/**
 * A custom object for a game
 */

public class Game
{
    public String gameTime;
    public String opponent;
    public int gameID;
    public GameResult gameResult;

    public Date gameTimeToDate()
    {
        if(gameTime == null){
            return null;
        }

        return DateFormaters.getDateFromGameTime(gameTime);
    }
}
