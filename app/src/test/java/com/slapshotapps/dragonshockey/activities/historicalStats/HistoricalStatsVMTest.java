package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HistoricalStatsVMTest {

    @Test
    public void testPlayerName(){

        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, null, null);

        assertThat(historicalStatsVM.getPlayerName(), is("Bob Builder"));
    }

    @Test
    public void testPlayerNameNull(){

        Player player = new Player();
        player.playerID = 9;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, null, null);

        assertThat(historicalStatsVM.getPlayerName(), is(""));
    }

    @Test
    public void testPlayerNumber()
    {
        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;
        player.number = 99;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, null, null);

        assertThat(historicalStatsVM.getPlayerNumber(), is("# 99"));
    }

    @Test
    public void testPlayerNumberEmpty()
    {
        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, null, null);

        assertThat(historicalStatsVM.getPlayerNumber(), is("# 0"));
    }

    @Test
    public void testPlayerPosition()
    {
        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;
        player.number = 99;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, null, null);

        assertThat(historicalStatsVM.getPlayerPosition(), is("Forward"));
    }

    @Test
    public void testCurrentSeasonFiltering(){

        List<GameStats> currentSeason = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            currentSeason.add(new GameStats());
            currentSeason.get(i).gameStats = getTeamStatsForGame();
        }

        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 2;
        player.number = 99;

        HistoricalStatsVM historicalStatsVM = new HistoricalStatsVM(player, currentSeason, null);

        PlayerHistoricalStats playerHistoricalStats = historicalStatsVM.getStats().get(0);

        int bobsGoals = 0;

        for(GameStats.Stats seasonStats : playerHistoricalStats.gamesStats){
            bobsGoals += seasonStats.goals;
        }

        assertThat(bobsGoals, is(15));

    }

    private List<GameStats.Stats> getTeamStatsForGame(){


        List<GameStats.Stats> stats = new ArrayList<GameStats.Stats>();

        int goals = 1;
        int assists = 1;
        for(int playerID = 0; playerID < 10; playerID++){
            stats.add(new GameStats.Stats(playerID, goals++, assists++, true));
        }

        return stats;
    }

}
