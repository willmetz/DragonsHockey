package com.slapshotapps.dragonshockey.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willmetz on 6/2/16.
 */

public class HockeySchedule
{
    public List<Game> games;

    public HockeySchedule()
    {
        games = new ArrayList<>();
    }

    public void addGame(Game game)
    {
        games.add(game);
    }

    public List<Game> getAllGames()
    {
        return games;
    }
}
