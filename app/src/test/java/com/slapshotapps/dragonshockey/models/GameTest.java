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

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 3;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseGameTime() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi3";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOpponent() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "wings";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOTL() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = true;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseDragons() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 10;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_falseOpponentScore() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 6;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertFalse(originalGame.equals(modifiedGame));
    }

    @Test
    public void testEquals_true() {
        Game originalGame = new Game();

        originalGame.gameID = 2;
        originalGame.gameTime = "hi";
        originalGame.opponent = "predators";
        originalGame.gameResult = new GameResult();
        originalGame.gameResult.dragonsScore = 9;
        originalGame.gameResult.opponentScore = 7;
        originalGame.gameResult.gameID = 2;
        originalGame.gameResult.overtimeLoss = false;

        Game modifiedGame = new Game();
        modifiedGame.gameID = 2;
        modifiedGame.gameTime = "hi";
        modifiedGame.opponent = "predators";
        modifiedGame.gameResult = new GameResult();
        modifiedGame.gameResult.dragonsScore = 9;
        modifiedGame.gameResult.opponentScore = 7;
        modifiedGame.gameResult.gameID = 2;
        modifiedGame.gameResult.overtimeLoss = false;

        Assert.assertTrue(originalGame.equals(modifiedGame));
    }
}
