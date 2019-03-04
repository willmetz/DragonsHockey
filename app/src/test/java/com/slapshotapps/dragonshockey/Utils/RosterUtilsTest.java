package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.Player;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by willmetz on 8/8/16.
 */
public class RosterUtilsTest {
    @Test
    public void getFullName() {

        Assert.assertNotNull("null player error", RosterUtils.getFullName(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getFullName(testPlayer));

        testPlayer.setFirstName("bob");

        Assert.assertEquals("FirstName", "Bob", RosterUtils.getFullName(testPlayer));

        testPlayer.setFirstName(null);
        testPlayer.setLastName("gonzo");

        Assert.assertEquals("FirstName", " Gonzo", RosterUtils.getFullName(testPlayer));

        testPlayer.setFirstName("bob");
        testPlayer.setLastName("gonzo");

        Assert.assertEquals("FirstName", "Bob Gonzo", RosterUtils.getFullName(testPlayer));
    }

    @Test
    public void getNumber() {

        Assert.assertNotNull("null player error", RosterUtils.getNumber(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getNumber(testPlayer));

        testPlayer.setNumber(56);

        Assert.assertEquals("Number check", "56", RosterUtils.getNumber(testPlayer));
    }

    @Test
    public void getPosition() {
        Assert.assertNotNull("null player error", RosterUtils.getPosition(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getPosition(testPlayer));

        testPlayer.setPosition(Player.Companion.getFORWARD());
        Assert.assertEquals("forward check", "Forward", RosterUtils.getPosition(testPlayer));

        testPlayer.setPosition(Player.Companion.getDEFENSE());
        Assert.assertEquals("forward check", "Defense", RosterUtils.getPosition(testPlayer));

        testPlayer.setPosition(Player.Companion.getGOALIE());
        Assert.assertEquals("forward check", "Goalie", RosterUtils.getPosition(testPlayer));

        testPlayer.setPosition("serew");
        Assert.assertEquals("Unknown check", "Unknown", RosterUtils.getPosition(testPlayer));
    }

    @Test
    public void sortRoster() {

        ArrayList<Player> roster = new ArrayList<>();

        roster.add(new Player(Player.Companion.getGOALIE()));
        roster.add(new Player(Player.Companion.getFORWARD()));
        roster.add(new Player(Player.Companion.getFORWARD()));
        roster.add(new Player(Player.Companion.getGOALIE()));
        roster.add(new Player(Player.Companion.getDEFENSE()));
        roster.add(new Player(Player.Companion.getFORWARD()));
        roster.add(new Player(Player.Companion.getDEFENSE()));
        roster.add(new Player(Player.Companion.getDEFENSE()));
        roster.add(new Player(Player.Companion.getFORWARD()));

        ArrayList<Player> sortedRoster = RosterUtils.sortRoster(roster);

        Assert.assertEquals(Player.Companion.getFORWARD(), sortedRoster.get(0).getPosition());
        Assert.assertEquals(Player.Companion.getFORWARD(), sortedRoster.get(1).getPosition());
        Assert.assertEquals(Player.Companion.getFORWARD(), sortedRoster.get(2).getPosition());
        Assert.assertEquals(Player.Companion.getFORWARD(), sortedRoster.get(3).getPosition());
        Assert.assertEquals(Player.Companion.getDEFENSE(), sortedRoster.get(4).getPosition());
        Assert.assertEquals(Player.Companion.getDEFENSE(), sortedRoster.get(5).getPosition());
        Assert.assertEquals(Player.Companion.getDEFENSE(), sortedRoster.get(6).getPosition());
        Assert.assertEquals(Player.Companion.getGOALIE(), sortedRoster.get(7).getPosition());
        Assert.assertEquals(Player.Companion.getGOALIE(), sortedRoster.get(8).getPosition());
    }
}