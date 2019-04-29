package com.slapshotapps.dragonshockey.models

import java.util.ArrayList
import java.util.Collections
import org.junit.Assert
import org.junit.Test

/**
 * Created by willmetz on 9/17/16.
 */

class PlayerStatsTest {

    @Test
    fun testSortDefault() {

        val playerStats = ArrayList<PlayerStats>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.points = 4
        playerStats.add(stats)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.points = 5
        playerStats.add(stats)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD)
        stats.points = 2
        playerStats.add(stats)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.points = 6
        playerStats.add(stats)

        Collections.sort(playerStats)

        Assert.assertEquals("zaon", playerStats[0].lastName)
        Assert.assertEquals("france", playerStats[1].lastName)
        Assert.assertEquals("joe", playerStats[2].lastName)
        Assert.assertEquals("wham", playerStats[3].lastName)
    }

    @Test
    fun testSortNameDefault() {

        val playerStats = ArrayList<PlayerStats>()

        var stats = PlayerStats(1, "bb", "bb", PlayerPosition.FORWARD)
        stats.points = 4
        playerStats.add(stats)

        stats = PlayerStats(1, "aa", "aa", PlayerPosition.FORWARD)
        stats.points = 4
        playerStats.add(stats)

        stats = PlayerStats(1, "aaa", "aaa", PlayerPosition.FORWARD)
        stats.points = 3
        playerStats.add(stats)

        Collections.sort(playerStats)

        Assert.assertEquals("aa", playerStats[0].lastName)
        Assert.assertEquals("bb", playerStats[1].lastName)
        Assert.assertEquals("aaa", playerStats[2].lastName)
    }
}
