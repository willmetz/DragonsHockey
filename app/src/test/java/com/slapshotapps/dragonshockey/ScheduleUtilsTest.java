package com.slapshotapps.dragonshockey;

import com.slapshotapps.dragonshockey.Utils.ScheduleUtils;
import com.slapshotapps.dragonshockey.models.Game;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A test for the schedule methods
 */
public class ScheduleUtilsTest {
  @Test public void testGameBeforeDate_dateInList() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 3);

    Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

    Assert.assertEquals(foundGame, games.get(1));
  }

  @Test public void testGameBeforeDate_dateMiddleList() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 4);

    Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

    Assert.assertEquals(foundGame, games.get(2));
  }

  @Test public void testGameBeforeDate_dateAfterGames() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 6);

    Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

    Assert.assertEquals(foundGame, games.get(2));
  }

  @Test public void testGameBeforeDate_dateBeforeGames() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 1);

    Game foundGame = ScheduleUtils.getGameBeforeDate(time.getTime(), games);

    Assert.assertNull(foundGame);
  }

  @Test public void testGameAfterDate_dateBeforeGames() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 1);

    Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

    Assert.assertEquals(foundGame, games.get(0));
  }

  @Test public void testGameAfterDate_dateAfterGames() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 5);

    Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

    Assert.assertNull(foundGame);
  }

  @Test public void testGameAfterDate_dateInGames() {
    ArrayList<Game> games =
        createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33");

    Calendar time = Calendar.getInstance();
    time.set(2016, Calendar.JUNE, 2);

    Game foundGame = ScheduleUtils.getGameAfterDate(time.getTime(), games);

    Assert.assertEquals(foundGame, games.get(1));
  }

  private ArrayList<Game> createScedule(String time1, String time2, String time3) {
    ArrayList<Game> games = new ArrayList<>();

    Game game1 = new Game();
    game1.gameTime = time1;
    game1.opponent = "firstGame";

    games.add(game1);

    Game game2 = new Game();
    game2.gameTime = time2;
    game2.opponent = "secondGame";
    games.add(game2);

    Game game3 = new Game();
    game3.gameTime = time3;
    game3.opponent = "thirdGame";
    games.add(game3);

    return games;
  }
}
