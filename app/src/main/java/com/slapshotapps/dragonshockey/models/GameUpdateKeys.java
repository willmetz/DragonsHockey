package com.slapshotapps.dragonshockey.models;

/**
 * Created on 10/30/16.
 */

public class GameUpdateKeys {

    public int gameResultKey;
    public int gameKey;
    public int gameStatsKey;

    public GameUpdateKeys(int gameKey, int gameResultKey, int gameStatsKey){
        this.gameKey = gameKey;
        this.gameResultKey = gameResultKey;
        this.gameStatsKey = gameStatsKey;
    }
}
