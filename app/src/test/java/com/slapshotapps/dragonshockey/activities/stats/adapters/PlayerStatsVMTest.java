package com.slapshotapps.dragonshockey.activities.stats.adapters;

import com.slapshotapps.dragonshockey.dialogs.StatSortSelection;
import com.slapshotapps.dragonshockey.models.PlayerPosition;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayerStatsVMTest {

    @Test
    public void testSortGoals() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.points = 4;
        stats.goals = 2;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.points = 5;
        stats.goals = 4;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.points = 2;
        stats.goals = 6;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.points = 6;
        stats.goals = 1;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD);
        stats.points = 6;
        stats.goals = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Goals);
        playerStats.add(viewModel);

        Collections.sort(playerStats);

        Assert.assertEquals("6", playerStats.get(0).getGoals());
        Assert.assertEquals("wham", playerStats.get(0).getLastName());

        Assert.assertEquals("4", playerStats.get(1).getGoals());
        Assert.assertEquals("france", playerStats.get(1).getLastName());

        Assert.assertEquals("2", playerStats.get(2).getGoals());
        Assert.assertEquals("aa", playerStats.get(2).getLastName());

        Assert.assertEquals("2", playerStats.get(3).getGoals());
        Assert.assertEquals("joe", playerStats.get(3).getLastName());

        Assert.assertEquals("1", playerStats.get(4).getGoals());
        Assert.assertEquals("zaon", playerStats.get(4).getLastName());
    }

    @Test
    public void testSortAssists() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.assists = 12;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.assists = 5;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.assists = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.assists = 1;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD);
        stats.assists = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Assists);
        playerStats.add(viewModel);

        Collections.sort(playerStats);

        Assert.assertEquals("12", playerStats.get(0).getAssists());
        Assert.assertEquals("joe", playerStats.get(0).getLastName());

        Assert.assertEquals("5", playerStats.get(1).getAssists());
        Assert.assertEquals("france", playerStats.get(1).getLastName());

        Assert.assertEquals("2", playerStats.get(2).getAssists());
        Assert.assertEquals("aa", playerStats.get(2).getLastName());

        Assert.assertEquals("2", playerStats.get(3).getAssists());
        Assert.assertEquals("wham", playerStats.get(3).getLastName());

        Assert.assertEquals("1", playerStats.get(4).getAssists());
        Assert.assertEquals("zaon", playerStats.get(4).getLastName());
    }

    @Test
    public void testSortPoints() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.assists = 12;
        stats.points = 33;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Points);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.assists = 5;
        stats.points = 22;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Points);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 18;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Points);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.assists = 1;
        stats.points = 22;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Points);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 3;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.Points);
        playerStats.add(viewModel);

        Collections.sort(playerStats);

        Assert.assertEquals("33", playerStats.get(0).getPoints());
        Assert.assertEquals("joe", playerStats.get(0).getLastName());

        Assert.assertEquals("22", playerStats.get(1).getPoints());
        Assert.assertEquals("france", playerStats.get(1).getLastName());

        Assert.assertEquals("22", playerStats.get(2).getPoints());
        Assert.assertEquals("zaon", playerStats.get(2).getLastName());

        Assert.assertEquals("18", playerStats.get(3).getPoints());
        Assert.assertEquals("wham", playerStats.get(3).getLastName());

        Assert.assertEquals("3", playerStats.get(4).getPoints());
        Assert.assertEquals("aa", playerStats.get(4).getLastName());
    }

    @Test
    public void testSortPenaltyMinutes() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.assists = 12;
        stats.points = 33;
        stats.penaltyMinutes = 2;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.assists = 5;
        stats.points = 22;
        stats.penaltyMinutes = 0;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 18;
        stats.penaltyMinutes = 12;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.assists = 1;
        stats.points = 22;
        stats.penaltyMinutes = 22;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 3;
        stats.penaltyMinutes = 12;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.PenaltyMinutes);
        playerStats.add(viewModel);

        Collections.sort(playerStats);

        Assert.assertEquals("22", playerStats.get(0).getPenaltyMinutes());
        Assert.assertEquals("zaon", playerStats.get(0).getLastName());

        Assert.assertEquals("12", playerStats.get(1).getPenaltyMinutes());
        Assert.assertEquals("aa", playerStats.get(1).getLastName());

        Assert.assertEquals("12", playerStats.get(2).getPenaltyMinutes());
        Assert.assertEquals("wham", playerStats.get(2).getLastName());

        Assert.assertEquals("2", playerStats.get(3).getPenaltyMinutes());
        Assert.assertEquals("joe", playerStats.get(3).getLastName());

        Assert.assertEquals("0", playerStats.get(4).getPenaltyMinutes());
        Assert.assertEquals("france", playerStats.get(4).getLastName());
    }

    @Test
    public void testSortGamesPlayed() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.FORWARD);
        stats.assists = 12;
        stats.points = 33;
        stats.penaltyMinutes = 2;
        stats.gamesPlayed = 8;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.GamesPlayed);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france", PlayerPosition.FORWARD);
        stats.assists = 5;
        stats.points = 22;
        stats.penaltyMinutes = 0;
        stats.gamesPlayed = 8;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.GamesPlayed);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 18;
        stats.penaltyMinutes = 12;
        stats.gamesPlayed = 7;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.GamesPlayed);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon", PlayerPosition.FORWARD);
        stats.assists = 1;
        stats.points = 22;
        stats.penaltyMinutes = 22;
        stats.gamesPlayed = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.GamesPlayed);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa", PlayerPosition.FORWARD);
        stats.assists = 2;
        stats.points = 3;
        stats.penaltyMinutes = 12;
        stats.gamesPlayed = 8;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatSortSelection.GamesPlayed);
        playerStats.add(viewModel);

        Collections.sort(playerStats);

        Assert.assertEquals("8", playerStats.get(0).getGamesPlayed());
        Assert.assertEquals("aa", playerStats.get(0).getLastName());

        Assert.assertEquals("8", playerStats.get(1).getGamesPlayed());
        Assert.assertEquals("france", playerStats.get(1).getLastName());

        Assert.assertEquals("8", playerStats.get(2).getGamesPlayed());
        Assert.assertEquals("joe", playerStats.get(2).getLastName());

        Assert.assertEquals("7", playerStats.get(3).getGamesPlayed());
        Assert.assertEquals("wham", playerStats.get(3).getLastName());

        Assert.assertEquals("2", playerStats.get(4).getGamesPlayed());
        Assert.assertEquals("zaon", playerStats.get(4).getLastName());
    }

    @Test
    public void testGoalsAgainstAverage() {
        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.GOALIE);
        stats.goalsAgainst = 2;
        stats.penaltyMinutes = 2;
        stats.gamesPlayed = 3;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);

        assertThat(viewModel.goalsAgainstAverage(), is("0.67"));
    }

    @Test
    public void testGoalsAgainstAverageNoGamesPlayed() {
        PlayerStats stats = new PlayerStats(1, "bob", "joe", PlayerPosition.GOALIE);
        stats.goalsAgainst = 0;
        stats.penaltyMinutes = 2;
        stats.gamesPlayed = 0;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);

        assertThat(viewModel.goalsAgainstAverage(), is("0.00"));
    }
}