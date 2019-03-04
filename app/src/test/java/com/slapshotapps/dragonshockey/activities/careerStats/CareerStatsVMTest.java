package com.slapshotapps.dragonshockey.activities.careerStats;

import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerPosition;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.models.SeasonStats;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CareerStatsVMTest {

    @Test
    public void testPlayerName() {

        Player player = new Player();
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(9);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerName(), is("Bob Builder"));
    }

    @Test
    public void testPlayerNameNull() {

        Player player = new Player();
        player.setPlayerID(9);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerName(), is(""));
    }

    @Test
    public void testPlayerNumber() {
        Player player = new Player();
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(9);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerNumber(), is("# 99"));
    }

    @Test
    public void testPlayerNumberEmpty() {
        Player player = new Player();
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(9);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerNumber(), is("# 0"));
    }

    @Test
    public void testPlayerPosition() {
        Player player = new Player("F");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(9);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, null);

        assertThat(careerStatsVM.getPlayerPosition(), is("Forward"));
    }

    @Test
    public void testNonGoalieCurrentSeasonAdding() {

        PlayerStats playerStats = new PlayerStats(2, "bob", "Builder", PlayerPosition.FORWARD);
        playerStats.setGoals(2);
        playerStats.setAssists(4);
        playerStats.setPenaltyMinutes(5);
        playerStats.setGamesPlayed(10);

        Player player = new Player("F");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(2);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, playerStats, null);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(2));
        assertThat(playerSeasonStats.get(0).getGoals(), is(2));
        assertThat(playerSeasonStats.get(0).getAssists(), is(4));
        assertThat(playerSeasonStats.get(0).getPenaltyMinutes(), is(5));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(6)));

        //career
        assertThat(playerSeasonStats.get(1).getGoals(), is(2));
        assertThat(playerSeasonStats.get(1).getAssists(), is(4));
        assertThat(playerSeasonStats.get(1).getPenaltyMinutes(), is(5));
        assertThat(playerSeasonStats.get(1).getPoints(), is(String.valueOf(6)));
        assertThat(playerSeasonStats.get(1).getSeasonID(), is("Career"));
    }

    @Test
    public void testGoalieCurrentSeasonAdding() {

        PlayerStats playerStats = new PlayerStats(2, "bob", "Builder", PlayerPosition.GOALIE);
        playerStats.setPenaltyMinutes(5);
        playerStats.setGamesPlayed(10);
        playerStats.setGoalsAgainst(18);

        Player player = new Player("G");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(2);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, playerStats, null);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(2));
        assertThat(playerSeasonStats.get(0).getPenaltyMinutes(), is(5));
        assertThat(playerSeasonStats.get(0).getGoalsAgainst(), is(String.valueOf(18)));
        assertThat(playerSeasonStats.get(0).getGoalsAgainstAverage(), is("1.80"));
        assertThat(playerSeasonStats.get(0).getGamesPlayed(), is(String.valueOf(10)));

        //career
        assertThat(playerSeasonStats.get(1).getGamesPlayed(), is(String.valueOf(10)));
        assertThat(playerSeasonStats.get(1).getGoalsAgainst(), is(String.valueOf(18)));
        assertThat(playerSeasonStats.get(1).getGoalsAgainstAverage(), is("1.80"));
        assertThat(playerSeasonStats.get(1).getPenaltyMinutes(), is(5));
        assertThat(playerSeasonStats.get(1).getSeasonID(), is("Career"));
    }

    @Test
    public void testNonGoalieHistoricalSeasonFiltering() {

        List<SeasonStats> seasonStats = new ArrayList<>();
        seasonStats.add(new SeasonStats());
        seasonStats.add(new SeasonStats());

        for (int i = 0; i < 5; i++) {
            seasonStats.get(0).getStats().add(new GameStats());
            seasonStats.get(0).getStats().get(i).setGameStats(getTeamStatsForGame());

            seasonStats.get(1).getStats().add(new GameStats());
            seasonStats.get(1).getStats().get(i).setGameStats(getTeamStatsForGame());
        }

        Player player = new Player("F");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(2);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, seasonStats);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(3));
        assertThat(playerSeasonStats.get(0).getGoals(), is(15));
        assertThat(playerSeasonStats.get(0).getAssists(), is(20));
        assertThat(playerSeasonStats.get(0).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(35)));

        assertThat(playerSeasonStats.get(1).getGoals(), is(15));
        assertThat(playerSeasonStats.get(1).getAssists(), is(20));
        assertThat(playerSeasonStats.get(1).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(1).getPoints(), is(String.valueOf(35)));

        assertThat(playerSeasonStats.get(2).getGoals(), is(30));
        assertThat(playerSeasonStats.get(2).getAssists(), is(40));
        assertThat(playerSeasonStats.get(2).getPenaltyMinutes(), is(20));
        assertThat(playerSeasonStats.get(2).getPoints(), is(String.valueOf(70)));
    }

    @Test
    public void testGoalieHistoricalSeasonFiltering() {

        List<SeasonStats> seasonStats = new ArrayList<>();
        seasonStats.add(new SeasonStats());
        seasonStats.add(new SeasonStats());

        for (int i = 0; i < 5; i++) {
            seasonStats.get(0).getStats().add(new GameStats());
            seasonStats.get(0).getStats().get(i).setGameStats(getTeamStatsForGame());

            seasonStats.get(1).getStats().add(new GameStats());
            seasonStats.get(1).getStats().get(i).setGameStats(getTeamStatsForGame());
        }

        Player player = new Player("G");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(4);
        player.setNumber(99);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, null, seasonStats);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(3));
        assertThat(playerSeasonStats.get(0).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(0).getGoalsAgainst(), is("20"));
        assertThat(playerSeasonStats.get(0).getGoalsAgainstAverage(), is("4.00"));
        assertThat(playerSeasonStats.get(0).getGamesPlayed(), is("5"));



        assertThat(playerSeasonStats.get(1).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(1).getGoalsAgainst(), is("20"));
        assertThat(playerSeasonStats.get(1).getGoalsAgainstAverage(), is("4.00"));
        assertThat(playerSeasonStats.get(1).getGamesPlayed(), is("5"));

        assertThat(playerSeasonStats.get(2).getPenaltyMinutes(), is(20));
        assertThat(playerSeasonStats.get(2).getGoalsAgainst(), is("40"));
        assertThat(playerSeasonStats.get(2).getGoalsAgainstAverage(), is("4.00"));
        assertThat(playerSeasonStats.get(2).getGamesPlayed(), is("10"));

    }

    @Test
    public void testFullCareer() {
        List<SeasonStats> seasonStats = new ArrayList<>();
        seasonStats.add(new SeasonStats("Winter 16"));
        seasonStats.add(new SeasonStats("Fall 17"));

        for (int i = 0; i < 5; i++) {
            seasonStats.get(0).getStats().add(new GameStats());
            seasonStats.get(0).getStats().get(i).setGameStats(getTeamStatsForGame());

            seasonStats.get(1).getStats().add(new GameStats());
            seasonStats.get(1).getStats().get(i).setGameStats(getTeamStatsForGame());
        }

        Player player = new Player("F");
        player.setFirstName("Bob");
        player.setLastName("Builder");
        player.setPlayerID(2);
        player.setNumber(99);

        PlayerStats playerStats = new PlayerStats(2, "bob", "Builder", PlayerPosition.FORWARD);
        playerStats.setGoals(2);
        playerStats.setAssists(4);
        playerStats.setGamesPlayed(10);
        playerStats.setPenaltyMinutes(8);

        CareerStatsVM careerStatsVM = new CareerStatsVM(player, playerStats, seasonStats);

        List<PlayerSeasonStatsVM> playerSeasonStats = careerStatsVM.getStats();

        assertThat(playerSeasonStats.size(), is(4));

        assertThat(playerSeasonStats.get(0).getGoals(), is(15));
        assertThat(playerSeasonStats.get(0).getAssists(), is(20));
        assertThat(playerSeasonStats.get(0).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(0).getPoints(), is(String.valueOf(35)));
        assertThat(playerSeasonStats.get(0).getSeasonID(), is("Winter 16"));

        assertThat(playerSeasonStats.get(1).getGoals(), is(15));
        assertThat(playerSeasonStats.get(1).getAssists(), is(20));
        assertThat(playerSeasonStats.get(1).getPenaltyMinutes(), is(10));
        assertThat(playerSeasonStats.get(1).getPoints(), is(String.valueOf(35)));
        assertThat(playerSeasonStats.get(1).getSeasonID(), is("Fall 17"));

        assertThat(playerSeasonStats.get(2).getGoals(), is(2));
        assertThat(playerSeasonStats.get(2).getAssists(), is(4));
        assertThat(playerSeasonStats.get(2).getPenaltyMinutes(), is(8));
        assertThat(playerSeasonStats.get(2).getPoints(), is(String.valueOf(6)));
        assertThat(playerSeasonStats.get(2).getSeasonID(), is("Current"));

        assertThat(playerSeasonStats.get(3).getGoals(), is(32));
        assertThat(playerSeasonStats.get(3).getAssists(), is(44));
        assertThat(playerSeasonStats.get(3).getPenaltyMinutes(), is(28));
        assertThat(playerSeasonStats.get(3).getPoints(), is(String.valueOf(76)));
        assertThat(playerSeasonStats.get(3).getSeasonID(), is("Career"));
    }

    private List<GameStats.Stats> getTeamStatsForGame() {

        List<GameStats.Stats> stats = new ArrayList<GameStats.Stats>();

        int goals = 1;
        int assists = 2;
        int goalsAgainst = 0;
        for (int playerID = 0; playerID < 10; playerID++) {
            stats.add(new GameStats.Stats(playerID, assists++, goals++, 2, true, goalsAgainst++));
        }

        return stats;
    }
}
