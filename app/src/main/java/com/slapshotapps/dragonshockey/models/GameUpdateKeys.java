package com.slapshotapps.dragonshockey.models;

import com.slapshotapps.dragonshockey.observables.AdminObserver;

/**
 * Created on 10/30/16.
 */

public class GameUpdateKeys {

    int gameResultKey;
    int gameKey;
    int gameStatsKey;

    public GameUpdateKeys(int gameKey, int gameResultKey, int gameStatsKey) {
        this.gameKey = gameKey;
        this.gameResultKey = gameResultKey;
        this.gameStatsKey = gameStatsKey;
    }

    public String getGameResultKey() {
        return String.valueOf(gameResultKey);
    }

    public String getGameKey() {
        return String.valueOf(gameKey);
    }

    public String getGameStatsKey() {
        return String.valueOf(gameStatsKey);
    }

    public boolean gameResultKeyValid() {
        return gameResultKey != AdminObserver.NO_KEY_FOUND;
    }

    public boolean gameKeyValid() {
        return gameKey != AdminObserver.NO_KEY_FOUND;
    }

    public boolean gameStatsKeyValid() {
        return gameStatsKey != AdminObserver.NO_KEY_FOUND;
    }
}
