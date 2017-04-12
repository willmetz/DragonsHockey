package com.slapshotapps.dragonshockey.activities.careerStats;


import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.models.SeasonStats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CareerStatsVMTest {

    @Test
    public void testPlayerName(){

        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerName(), is("Bob Builder"));
    }

    @Test
    public void testPlayerNameNull(){

        Player player = new Player();
        player.playerID = 9;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerName(), is(""));
    }

    @Test
    public void testPlayerNumber()
    {
        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;
        player.number = 99;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerNumber(), is("# 99"));
    }

    @Test
    public void testPlayerNumberEmpty()
    {
        Player player = new Player();
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerNumber(), is("# 0"));
    }

    @Test
    public void testPlayerPosition()
    {
        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 9;
        player.number = 99;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerPosition(), is("Forward"));
    }

    @Test
    public void testCurrentSeasonAdding(){

        PlayerStats playerStats = new PlayerStats(2, "bob", "Builder");
        playerStats.goals = 2;
        playerStats.assists = 4;
        playerStats.gamesPlayed = 10;

        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 2;
        player.number = 99;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, playerStats, null);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(1));
        assertThat(playerSeasonStats.get(0).goals, is(2));
        assertThat(playerSeasonStats.get(0).assists, is(4));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(6)));
    }

    @Test
    public void testHistoricalSeasonFiltering(){

        List<SeasonStats> seasonStats = new ArrayList<>();
        seasonStats.add(new SeasonStats());
        seasonStats.add(new SeasonStats());

        for(int i = 0; i < 5; i++){
            seasonStats.get(0).stats.add(new GameStats());
            seasonStats.get(0).stats.get(i).gameStats = getTeamStatsForGame();


            seasonStats.get(1).stats.add(new GameStats());
            seasonStats.get(1).stats.get(i).gameStats = getTeamStatsForGame();
        }

        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 2;
        player.number = 99;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, seasonStats);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(2));
        assertThat(playerSeasonStats.get(0).goals, is(15));
        assertThat(playerSeasonStats.get(0).assists, is(20));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(35)));


        assertThat(playerSeasonStats.get(1).goals, is(15));
        assertThat(playerSeasonStats.get(1).assists, is(20));
        assertThat(playerSeasonStats.get(1).getPoints(), is(String.valueOf(35)));
    }

    @Test
    public void testFullCareer(){
        List<SeasonStats> seasonStats = new ArrayList<>();
        seasonStats.add(new SeasonStats("Winter 16"));
        seasonStats.add(new SeasonStats("Fall 17"));

        for(int i = 0; i < 5; i++){
            seasonStats.get(0).stats.add(new GameStats());
            seasonStats.get(0).stats.get(i).gameStats = getTeamStatsForGame();


            seasonStats.get(1).stats.add(new GameStats());
            seasonStats.get(1).stats.get(i).gameStats = getTeamStatsForGame();
        }

        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 2;
        player.number = 99;

        PlayerStats playerStats = new PlayerStats(2, "bob", "Builder");
        playerStats.goals = 2;
        playerStats.assists = 4;
        playerStats.gamesPlayed = 10;

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, playerStats, seasonStats);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(3));

        assertThat(playerSeasonStats.get(0).goals, is(15));
        assertThat(playerSeasonStats.get(0).assists, is(20));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(35)));
        assertThat(playerSeasonStats.get(0).seasonID, is("Winter 16"));


        assertThat(playerSeasonStats.get(1).goals, is(15));
        assertThat(playerSeasonStats.get(1).assists, is(20));
        assertThat(playerSeasonStats.get(1).getPoints(), is(String.valueOf(35)));
        assertThat(playerSeasonStats.get(1).seasonID, is("Fall 17"));


        assertThat(playerSeasonStats.get(2).goals, is(2));
        assertThat(playerSeasonStats.get(2).assists, is(4));
        assertThat(playerSeasonStats.get(2).getPoints(), is(String.valueOf(6)));
        assertThat(playerSeasonStats.get(2).seasonID, is("Current"));
    }

    private List<GameStats.Stats> getTeamStatsForGame(){


        List<GameStats.Stats> stats = new ArrayList<GameStats.Stats>();

        int goals = 1;
        int assists = 2;
        for(int playerID = 0; playerID < 10; playerID++){
            stats.add(new GameStats.Stats(playerID, assists++,  goals++, true));
        }

        return stats;
    }

}
