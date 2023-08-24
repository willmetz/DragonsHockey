package com.slapshotapps.dragonshockey.utils

import com.slapshotapps.dragonshockey.models.Player
import java.util.ArrayList
import junit.framework.Assert
import org.junit.Test

/**
 * Created by willmetz on 8/8/16.
 */
class RosterUtilsTest {
    @Test
    fun getFullName() {

        Assert.assertNotNull("null player error", RosterUtils.getFullName(null))

        val testPlayer = Player()

        Assert.assertNotNull("empty player error", RosterUtils.getFullName(testPlayer))

        testPlayer.firstName = "bob"

        Assert.assertEquals("FirstName", "Bob", RosterUtils.getFullName(testPlayer))

        testPlayer.firstName = null
        testPlayer.lastName = "gonzo"

        Assert.assertEquals("FirstName", " Gonzo", RosterUtils.getFullName(testPlayer))

        testPlayer.firstName = "bob"
        testPlayer.lastName = "gonzo"

        Assert.assertEquals("FirstName", "Bob Gonzo", RosterUtils.getFullName(testPlayer))
    }

    @Test
    fun getNumber() {

        Assert.assertNotNull("null player error", RosterUtils.getNumber(null))

        val testPlayer = Player()

        Assert.assertNotNull("empty player error", RosterUtils.getNumber(testPlayer))

        testPlayer.number = 56

        Assert.assertEquals("Number check", "56", RosterUtils.getNumber(testPlayer))
    }

    @Test
    fun getPosition() {
        Assert.assertNotNull("null player error", RosterUtils.getPosition(null))

        val testPlayer = Player()

        Assert.assertNotNull("empty player error", RosterUtils.getPosition(testPlayer))

        testPlayer.position = Player.FORWARD
        Assert.assertEquals("forward check", "Forward", RosterUtils.getPosition(testPlayer))

        testPlayer.position = Player.DEFENSE
        Assert.assertEquals("forward check", "Defense", RosterUtils.getPosition(testPlayer))

        testPlayer.position = Player.GOALIE
        Assert.assertEquals("forward check", "Goalie", RosterUtils.getPosition(testPlayer))

        testPlayer.position = "serew"
        Assert.assertEquals("Unknown check", "Unknown", RosterUtils.getPosition(testPlayer))
    }

    @Test
    fun sortRoster() {

        val roster = ArrayList<Player>()

        roster.add(Player(Player.GOALIE))
        roster.add(Player(Player.FORWARD))
        roster.add(Player(Player.FORWARD))
        roster.add(Player(Player.GOALIE))
        roster.add(Player(Player.DEFENSE))
        roster.add(Player(Player.FORWARD))
        roster.add(Player(Player.DEFENSE))
        roster.add(Player(Player.DEFENSE))
        roster.add(Player(Player.FORWARD))

        val sortedRoster = RosterUtils.sortRoster(roster)

        Assert.assertEquals(Player.FORWARD, sortedRoster[0].position)
        Assert.assertEquals(Player.FORWARD, sortedRoster[1].position)
        Assert.assertEquals(Player.FORWARD, sortedRoster[2].position)
        Assert.assertEquals(Player.FORWARD, sortedRoster[3].position)
        Assert.assertEquals(Player.DEFENSE, sortedRoster[4].position)
        Assert.assertEquals(Player.DEFENSE, sortedRoster[5].position)
        Assert.assertEquals(Player.DEFENSE, sortedRoster[6].position)
        Assert.assertEquals(Player.GOALIE, sortedRoster[7].position)
        Assert.assertEquals(Player.GOALIE, sortedRoster[8].position)
    }
}