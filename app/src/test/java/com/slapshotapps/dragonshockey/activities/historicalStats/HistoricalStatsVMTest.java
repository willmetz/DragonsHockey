package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HistoricalStatsVMTest {

    @Test
    public void testSeasonID(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.seasonID = "Winter '17";
        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(playerHistoricalStats);

        assertThat(historicalStatsVM.getSeasonName(), is("Winter '17"));
    }

    @Test
    public void testSeasonIDEmpty(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(playerHistoricalStats);

        assertThat(historicalStatsVM.getSeasonName(), is(""));
    }
}
