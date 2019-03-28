package com.slapshotapps.dragonshockey.activities.stats.adapters

import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.models.PlayerPosition
import com.slapshotapps.dragonshockey.models.PlayerStats
import java.util.ArrayList
import java.util.Collections
import org.junit.Assert
import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class PlayerStatsVMTest {

    @Test
    fun testSortGoals() {

        val playerStats = ArrayList<PlayerStatsVM>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.points = 4
        stats.goals = 2
        var viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Goals)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.points = 5
        stats.goals = 4
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Goals)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.GOALIE)
        stats.points = 2
        stats.goals = 6
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Goals)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.points = 6
        stats.goals = 1
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Goals)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD)
        stats.points = 6
        stats.goals = 2
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Goals)
        playerStats.add(viewModel)

        Collections.sort(playerStats)

        Assert.assertEquals("4", playerStats[0].goals)
        Assert.assertEquals("france", playerStats[0].lastName)

        Assert.assertEquals("2", playerStats[1].goals)
        Assert.assertEquals("aa", playerStats[1].lastName)

        Assert.assertEquals("2", playerStats[2].goals)
        Assert.assertEquals("joe", playerStats[2].lastName)

        Assert.assertEquals("1", playerStats[3].goals)
        Assert.assertEquals("zaon", playerStats[3].lastName)

        Assert.assertEquals("6", playerStats[4].goals)
        Assert.assertEquals("wham", playerStats[4].lastName)
    }

    @Test
    fun testSortAssists() {

        val playerStats = ArrayList<PlayerStatsVM>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.assists = 12
        var viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Assists)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.assists = 5
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Assists)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD)
        stats.assists = 2
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Assists)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.assists = 1
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Assists)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "bob", "aa", PlayerPosition.GOALIE)
        stats.assists = 2
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Assists)
        playerStats.add(viewModel)

        Collections.sort(playerStats)

        Assert.assertEquals("12", playerStats[0].assists)
        Assert.assertEquals("joe", playerStats[0].lastName)

        Assert.assertEquals("5", playerStats[1].assists)
        Assert.assertEquals("france", playerStats[1].lastName)

        Assert.assertEquals("2", playerStats[2].assists)
        Assert.assertEquals("wham", playerStats[2].lastName)

        Assert.assertEquals("1", playerStats[3].assists)
        Assert.assertEquals("zaon", playerStats[3].lastName)

        Assert.assertEquals("2", playerStats[4].assists)
        Assert.assertEquals("aa", playerStats[4].lastName)
    }

    @Test
    fun testSortPoints() {

        val playerStats = ArrayList<PlayerStatsVM>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.assists = 12
        stats.points = 33
        var viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Points)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.assists = 5
        stats.points = 22
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Points)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.GOALIE)
        stats.assists = 2
        stats.points = 18
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Points)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.assists = 1
        stats.points = 22
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Points)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD)
        stats.assists = 2
        stats.points = 3
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.Points)
        playerStats.add(viewModel)

        Collections.sort(playerStats)

        Assert.assertEquals("33", playerStats[0].points)
        Assert.assertEquals("joe", playerStats[0].lastName)

        Assert.assertEquals("22", playerStats[1].points)
        Assert.assertEquals("france", playerStats[1].lastName)

        Assert.assertEquals("22", playerStats[2].points)
        Assert.assertEquals("zaon", playerStats[2].lastName)

        Assert.assertEquals("3", playerStats[3].points)
        Assert.assertEquals("aa", playerStats[3].lastName)

        Assert.assertEquals("18", playerStats[4].points)
        Assert.assertEquals("wham", playerStats[4].lastName)
    }

    @Test
    fun testSortPenaltyMinutes() {

        val playerStats = ArrayList<PlayerStatsVM>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.assists = 12
        stats.points = 33
        stats.penaltyMinutes = 2
        var viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.assists = 5
        stats.points = 22
        stats.penaltyMinutes = 0
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD)
        stats.assists = 2
        stats.points = 18
        stats.penaltyMinutes = 12
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.assists = 1
        stats.points = 22
        stats.penaltyMinutes = 22
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD)
        stats.assists = 2
        stats.points = 3
        stats.penaltyMinutes = 12
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes)
        playerStats.add(viewModel)

        Collections.sort(playerStats)

        Assert.assertEquals("22", playerStats[0].penaltyMinutes)
        Assert.assertEquals("zaon", playerStats[0].lastName)

        Assert.assertEquals("12", playerStats[1].penaltyMinutes)
        Assert.assertEquals("aa", playerStats[1].lastName)

        Assert.assertEquals("12", playerStats[2].penaltyMinutes)
        Assert.assertEquals("wham", playerStats[2].lastName)

        Assert.assertEquals("2", playerStats[3].penaltyMinutes)
        Assert.assertEquals("joe", playerStats[3].lastName)

        Assert.assertEquals("0", playerStats[4].penaltyMinutes)
        Assert.assertEquals("france", playerStats[4].lastName)
    }

    @Test
    fun testSortGamesPlayed() {

        val playerStats = ArrayList<PlayerStatsVM>()

        var stats = PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD)
        stats.assists = 12
        stats.points = 33
        stats.penaltyMinutes = 2
        stats.gamesPlayed = 8
        var viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.GamesPlayed)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD)
        stats.assists = 5
        stats.points = 22
        stats.penaltyMinutes = 0
        stats.gamesPlayed = 8
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.GamesPlayed)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD)
        stats.assists = 2
        stats.points = 18
        stats.penaltyMinutes = 12
        stats.gamesPlayed = 7
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.GamesPlayed)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD)
        stats.assists = 1
        stats.points = 22
        stats.penaltyMinutes = 22
        stats.gamesPlayed = 2
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.GamesPlayed)
        playerStats.add(viewModel)

        stats = PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD)
        stats.assists = 2
        stats.points = 3
        stats.penaltyMinutes = 12
        stats.gamesPlayed = 8
        viewModel = PlayerStatsVM(stats, null)
        viewModel.setSortSelection(StatSortSelection.GamesPlayed)
        playerStats.add(viewModel)

        Collections.sort(playerStats)

        Assert.assertEquals("8", playerStats[0].gamesPlayed)
        Assert.assertEquals("aa", playerStats[0].lastName)

        Assert.assertEquals("8", playerStats[1].gamesPlayed)
        Assert.assertEquals("france", playerStats[1].lastName)

        Assert.assertEquals("8", playerStats[2].gamesPlayed)
        Assert.assertEquals("joe", playerStats[2].lastName)

        Assert.assertEquals("7", playerStats[3].gamesPlayed)
        Assert.assertEquals("wham", playerStats[3].lastName)

        Assert.assertEquals("2", playerStats[4].gamesPlayed)
        Assert.assertEquals("zaon", playerStats[4].lastName)
    }

    @Test
    fun testGoalsAgainstAverage() {
        val stats = PlayerStats(1, "bob", "joe", PlayerPosition.GOALIE)
        stats.goalsAgainst = 2
        stats.penaltyMinutes = 2
        stats.gamesPlayed = 3
        val viewModel = PlayerStatsVM(stats, null)

        assertThat(viewModel.goalsAgainstAverage(), `is`("0.67"))
    }

    @Test
    fun testGoalsAgainstAverageNoGamesPlayed() {
        val stats = PlayerStats(1, "bob", "joe", PlayerPosition.GOALIE)
        stats.goalsAgainst = 0
        stats.penaltyMinutes = 2
        stats.gamesPlayed = 0
        val viewModel = PlayerStatsVM(stats, null)

        assertThat(viewModel.goalsAgainstAverage(), `is`("0.00"))
    }
}