package com.slapshotapps.dragonshockey.models;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by willmetz on 9/17/16.
 */

public class PlayerStatsTest {

    @Test
    public void testSortDefault() {

        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.setPoints(4);
        playerStats.add(stats);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.setPoints(5);
        playerStats.add(stats);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.setPoints(2);
        playerStats.add(stats);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.setPoints(6);
        playerStats.add(stats);

        Collections.sort(playerStats);

        Assert.assertEquals("zaon", playerStats.get(0).getLastName());
        Assert.assertEquals("france", playerStats.get(1).getLastName());
        Assert.assertEquals("joe", playerStats.get(2).getLastName());
        Assert.assertEquals("wham", playerStats.get(3).getLastName());
    }

    @Test
    public void testSortNameDefault() {

        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bb", "bb", PlayerPosition.FORWARD);
        stats.setPoints(4);
        playerStats.add(stats);

        stats = new PlayerStats(1, "aa", "aa", PlayerPosition.FORWARD);
        stats.setPoints(4);
        playerStats.add(stats);

        stats = new PlayerStats(1, "aaa", "aaa", PlayerPosition.FORWARD);
        stats.setPoints(3);
        playerStats.add(stats);

        Collections.sort(playerStats);

        Assert.assertEquals("aa", playerStats.get(0).getLastName());
        Assert.assertEquals("bb", playerStats.get(1).getLastName());
        Assert.assertEquals("aaa", playerStats.get(2).getLastName());
    }
}
