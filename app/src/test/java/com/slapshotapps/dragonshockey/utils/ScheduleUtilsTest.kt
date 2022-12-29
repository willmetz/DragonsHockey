package com.slapshotapps.dragonshockey.utils

import com.slapshotapps.dragonshockey.models.Game
import java.util.ArrayList
import java.util.Calendar
import junit.framework.Assert
import org.junit.Test

/**
 * A test for the schedule methods
 */
class ScheduleUtilsTest {
    @Test
    fun testGameBeforeDate_dateInList() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 3, 18, 0, 0)

        val foundGame = ScheduleUtils.getGameBeforeDate(time.time, games)

        Assert.assertEquals(foundGame, games[1])
    }

    @Test
    fun testGameBeforeDate_dateMiddleList() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 4, 18, 0, 0)

        val foundGame = ScheduleUtils.getGameBeforeDate(time.time, games)

        Assert.assertEquals(foundGame, games[2])
    }

    @Test
    fun testGameBeforeDate_dateAfterGames() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 6, 18, 0, 0)

        val foundGame = ScheduleUtils.getGameBeforeDate(time.time, games)

        Assert.assertEquals(foundGame, games[2])
    }

    @Test
    fun testGameBeforeDate_dateBeforeGames() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 1, 18, 0, 0)

        val foundGame = ScheduleUtils.getGameBeforeDate(time.time, games)

        Assert.assertNull(foundGame)
    }

    @Test
    fun testGameAfterDate_dateBeforeGames() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 2, 16, 0, 0)

        val foundGame = ScheduleUtils.getGameAfterDate(time.time, games)

        Assert.assertEquals(foundGame, games[0])
    }

    @Test
    fun testGameAfterDate_dateAfterGames() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 5, 18, 0, 0)

        val foundGame = ScheduleUtils.getGameAfterDate(time.time, games)

        Assert.assertNull(foundGame)
    }

    @Test
    fun testGameAfterDate_dateInGames() {
        val games = createScedule("2016-06-02 17:18:33", "2016-06-03 17:18:33", "2016-06-04 17:18:33")

        val time = Calendar.getInstance()
        time.set(2016, Calendar.JUNE, 2, 19, 0, 0)

        val foundGame = ScheduleUtils.getGameAfterDate(time.time, games)

        Assert.assertEquals(foundGame, games[1])
    }

    private fun createScedule(time1: String, time2: String, time3: String): ArrayList<Game> {
        val games = ArrayList<Game>()

        val game1 = Game()
        game1.gameTime = time1
        game1.opponent = "firstGame"

        games.add(game1)

        val game2 = Game()
        game2.gameTime = time2
        game2.opponent = "secondGame"
        games.add(game2)

        val game3 = Game()
        game3.gameTime = time3
        game3.opponent = "thirdGame"
        games.add(game3)

        return games
    }
}
