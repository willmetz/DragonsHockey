package com.slapshotapps.dragonshockey.activities.stats.adapters;

import com.slapshotapps.dragonshockey.dialogs.StatsSortDialogFragment;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class PlayerStatsVMTest {

    @Test
    public void testSortGoals() {

        ArrayList<PlayerStatsVM> playerStats = new ArrayList<>();

        PlayerStats stats = new PlayerStats(1, "bob", "joe");
        stats.points = 4;
        stats.goals = 2;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france");
        stats.points = 5;
        stats.goals = 4;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham");
        stats.points = 2;
        stats.goals = 6;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon");
        stats.points = 6;
        stats.goals = 1;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Goals);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa");
        stats.points = 6;
        stats.goals = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Goals);
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

        PlayerStats stats = new PlayerStats(1, "bob", "joe");
        stats.assists = 12;
        PlayerStatsVM viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "jeff", "france");
        stats.assists = 5;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "dan", "wham");
        stats.assists = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "will", "zaon");
        stats.assists = 1;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Assists);
        playerStats.add(viewModel);

        stats = new PlayerStats(1, "bob", "aa");
        stats.assists = 2;
        viewModel = new PlayerStatsVM(stats, null);
        viewModel.setSortSelection(StatsSortDialogFragment.StatSortSelection.Assists);
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
}