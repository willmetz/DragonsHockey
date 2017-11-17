package com.slapshotapps.dragonshockey.models;

import android.support.annotation.Keep;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that will hold the schedule for the season
 */
@Keep
public class SeasonSchedule {
    public List<Game> games;

    public SeasonSchedule() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public List<Game> getAllGames() {
        return games;
    }

    public int numberOfGames() {
        return games.size();
    }

    public Game getGame(int gameID) {
        for (Game game : games) {
            if (game.gameID == gameID) {
                return game;
            }
        }

        return null;
    }
}
