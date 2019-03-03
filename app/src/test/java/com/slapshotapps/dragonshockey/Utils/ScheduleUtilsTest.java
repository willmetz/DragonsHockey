package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.Game;
import java.util.ArrayList;
import java.util.Calendar;
import junit.framework.Assert;
import org.junit.Test;

/**
 * A test for the schedule methods
 */
public class ScheduleUtilsTest {
    @Test
    public void testGameBeforeDate_dateInList() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 3, 18, 0, 0);

        Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

        Assert.assertEquals(foundGame, games.get(1));
    }

    @Test
    public void testGameBeforeDate_dateMiddleList() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 4, 18, 0, 0);

        Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

        Assert.assertEquals(foundGame, games.get(2));
    }

    @Test
    public void testGameBeforeDate_dateAfterGames() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 6, 18, 0, 0);

        Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

        Assert.assertEquals(foundGame, games.get(2));
    }

    @Test
    public void testGameBeforeDate_dateBeforeGames() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 1, 18, 0, 0);

        Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

        Assert.assertNull(foundGame);
    }

    @Test
    public void testGameAfterDate_dateBeforeGames() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 2, 16, 0, 0);

        Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

        Assert.assertEquals(foundGame, games.get(0));
    }

    @Test
    public void testGameAfterDate_dateAfterGames() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 5, 18, 0, 0);

        Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

        Assert.assertNull(foundGame);
    }

    @Test
    public void testGameAfterDate_dateInGames() {
        ArrayList<Game> games =
            createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

        Calendar time = Calendar.getInstance();
        time.set(2016, Calendar.JUNE, 2, 19, 0, 0);

        Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

        Assert.assertEquals(foundGame, games.get(1));
    }

    private ArrayList<Game> createScedule(String time1, String time2, String time3) {
        ArrayList<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setGameTime(time1);
        game1.setOpponent("firstGame");

        games.add(game1);

        Game game2 = new Game();
        game2.setGameTime(time2);
        game2.setOpponent("secondGame");
        games.add(game2);

        Game game3 = new Game();
        game3.setGameTime(time3);
        game3.setOpponent("thirdGame");
        games.add(game3);

        return games;
    }
}
