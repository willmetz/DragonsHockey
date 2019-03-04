package com.slapshotapps.dragonshockey.models;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 10/21/16.
 */

public class GameTest {

    @Test
    public void testEquals_falseGameID() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(3);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseGameTime() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi3");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOpponent() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("wings");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOTL() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(true);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseDragons() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(10);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOpponentScore() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(6);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_true() {
        Game originalGame = new Game();

        originalGame.setGameID(2);
        originalGame.setGameTime("hi");
        originalGame.setOpponent("predators");
        originalGame.setGameResult(new GameResult());
        originalGame.getGameResult().setDragonsScore(9);
        originalGame.getGameResult().setOpponentScore(7);
        originalGame.getGameResult().setGameID(2);
        originalGame.getGameResult().setOvertimeLoss(false);

        Game modifiedGame = new Game();
        modifiedGame.setGameID(2);
        modifiedGame.setGameTime("hi");
        modifiedGame.setOpponent("predators");
        modifiedGame.setGameResult(new GameResult());
        modifiedGame.getGameResult().setDragonsScore(9);
        modifiedGame.getGameResult().setOpponentScore(7);
        modifiedGame.getGameResult().setGameID(2);
        modifiedGame.getGameResult().setOvertimeLoss(false);

        Assert.assertTrue(originalGame.equals(modifiedGame));
    }
}
