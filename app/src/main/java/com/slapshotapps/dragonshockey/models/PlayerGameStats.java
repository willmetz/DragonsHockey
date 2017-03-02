package com.slapshotapps.dragonshockey.models;

import android.support.annotation.Keep;

import java.util.ArrayList;

/**
 * Created on 11/5/16.
 */
@Keep
public class PlayerGameStats {

    public String playerStatsKey;
    public ArrayList<Player> players;
    public GameStats playerGameStats;

    public boolean isKeyValid() {
        return playerStatsKey != null;
    }

}
