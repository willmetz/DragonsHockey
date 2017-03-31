package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HistoricalStatsRowVMTest {

    @Test
    public void testSeasonID(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.seasonID = "Winter '17";
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getSeasonName(), is("Winter '17"));
    }

    @Test
    public void testSeasonIDEmpty(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getSeasonName(), is(""));
    }


    @Test
    public void testGamesPlayed_None(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getGamesPlayed(), is(0));
    }

    @Test
    public void testGamesPlayed_One(){

        ArrayList<GameStats> seasonStats = new ArrayList<>();
        GameStats gameStats = new GameStats();
        ArrayList<GameStats.Stats> playerInGameStats = new ArrayList<>();
        playerInGameStats.add(new GameStats.Stats(0, 1, 1, true));

        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.gamesStats = playerInGameStats;
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getGamesPlayed(), is(1));
    }

    @Test
    public void testGamesPlayed_twoOfThree(){

        ArrayList<GameStats.Stats> playerInGameStats = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            playerInGameStats.add(new GameStats.Stats(0, 1, 1, i != 1));
        }

        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.gamesStats = playerInGameStats;
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getGamesPlayed(), is(2));
    }

    @Test
    public void testGoals_Two(){

        ArrayList<GameStats.Stats> playerInGameStats = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            playerInGameStats.add(new GameStats.Stats(0, 0, 1, true));
        }

        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.gamesStats = playerInGameStats;
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getGoals(), is(2));
    }

    @Test
    public void testGoals_None(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getGoals(), is(0));
    }

    @Test
    public void testAssists_Three(){

        ArrayList<GameStats.Stats> playerInGameStats = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            playerInGameStats.add(new GameStats.Stats(0, i == 0?1:2, 0, true));
        }

        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.gamesStats = playerInGameStats;
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getAssists(), is(3));
    }


    @Test
    public void testAssists_None(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getAssists(), is(0));
    }

    @Test
    public void testPoins_Twelve(){

        ArrayList<GameStats.Stats> playerInGameStats = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            playerInGameStats.add(new GameStats.Stats(0, 2, 2, true));
        }

        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        playerHistoricalStats.gamesStats = playerInGameStats;
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getPoints(), is(12));
    }

    @Test
    public void testPoints_None(){
        PlayerHistoricalStats playerHistoricalStats = new PlayerHistoricalStats();
        HistoricalStatsRowVM historicalStatsRowVM = new HistoricalStatsRowVM(playerHistoricalStats);

        assertThat(historicalStatsRowVM.getPoints(), is(0));
    }
}
