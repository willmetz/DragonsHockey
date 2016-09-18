package com.slapshotapps.dragonshockey.models;

import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerStats;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by willmetz on 9/17/16.
 */

public class PlayerStatsTest {

    @Test
    public void testSortPoints(){

        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe");
        stats.points = 4;
        playerStats.add(stats);

        stats = new PlayerStats(1, "jeff", "france");
        stats.points = 5;
        playerStats.add(stats);

        stats = new PlayerStats(1, "dan", "wham");
        stats.points = 2;
        playerStats.add(stats);

        stats = new PlayerStats(1, "will", "zaon");
        stats.points = 6;
        playerStats.add(stats);

        Collections.sort(playerStats);

        Assert.assertEquals("zaon", playerStats.get(0).lastName);
        Assert.assertEquals("france", playerStats.get(1).lastName);
        Assert.assertEquals("joe", playerStats.get(2).lastName);
        Assert.assertEquals("wham", playerStats.get(3).lastName);
    }

    @Test
    public void testSortName(){

        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bb", "bb");
        stats.points = 4;
        playerStats.add(stats);

        stats = new PlayerStats(1, "aa", "aa");
        stats.points = 4;
        playerStats.add(stats);

        stats = new PlayerStats(1, "aaa", "aaa");
        stats.points = 3;
        playerStats.add(stats);

        Collections.sort(playerStats);

        Assert.assertEquals("aa", playerStats.get(0).lastName);
        Assert.assertEquals("bb", playerStats.get(1).lastName);
        Assert.assertEquals("aaa", playerStats.get(2).lastName);

    }
}
