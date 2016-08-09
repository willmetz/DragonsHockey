package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.Player;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by willmetz on 8/8/16.
 */
public class RosterUtilsTest {
    @Test
    public void getFullName(){

        Assert.assertNotNull("null player error", RosterUtils.getFullName(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getFullName(testPlayer));

        testPlayer.firstName = "bob";

        Assert.assertEquals("FirstName","Bob",RosterUtils.getFullName(testPlayer));

        testPlayer.firstName = null;
        testPlayer.lastName = "gonzo";

        Assert.assertEquals("FirstName"," Gonzo",RosterUtils.getFullName(testPlayer));

        testPlayer.firstName = "bob";
        testPlayer.lastName = "gonzo";

        Assert.assertEquals("FirstName","Bob Gonzo",RosterUtils.getFullName(testPlayer));
    }

    @Test
    public void getNumber() {

        Assert.assertNotNull("null player error", RosterUtils.getNumber(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getNumber(testPlayer));

        testPlayer.number = 56;

        Assert.assertEquals("Number check", "56", RosterUtils.getNumber(testPlayer));

    }

    @Test
    public void getPosition() {
        Assert.assertNotNull("null player error", RosterUtils.getPosition(null));

        Player testPlayer = new Player();

        Assert.assertNotNull("empty player error", RosterUtils.getPosition(testPlayer));

        testPlayer.position = "F";
        Assert.assertEquals("forward check", "Forward", RosterUtils.getPosition(testPlayer));

        testPlayer.position = "D";
        Assert.assertEquals("forward check", "Defense", RosterUtils.getPosition(testPlayer));

        testPlayer.position = "G";
        Assert.assertEquals("forward check", "Goalie", RosterUtils.getPosition(testPlayer));

        testPlayer.position = "ukef";
        Assert.assertEquals("Unknown check", "Unknown", RosterUtils.getPosition(testPlayer));
    }


}