package com.slapshotapps.dragonshockey.models

import org.junit.Assert
import org.junit.Test

/**
 * Created on 10/21/16.
 */

class GameTest {

    @Test
    fun testEquals_falseGameID() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 3
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_falseGameTime() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi3"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_falseOpponent() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "wings"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_falseOTL() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = true

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_falseDragons() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 10
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_falseOpponentScore() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 6
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertFalse(originalGame == modifiedGame)
    }

    @Test
    fun testEquals_true() {
        val originalGame = Game()

        originalGame.gameID = 2
        originalGame.gameTime = "hi"
        originalGame.opponent = "predators"
        originalGame.gameResult = GameResult()
        originalGame.gameResult!!.dragonsScore = 9
        originalGame.gameResult!!.opponentScore = 7
        originalGame.gameResult!!.gameID = 2
        originalGame.gameResult!!.overtimeLoss = false

        val modifiedGame = Game()
        modifiedGame.gameID = 2
        modifiedGame.gameTime = "hi"
        modifiedGame.opponent = "predators"
        modifiedGame.gameResult = GameResult()
        modifiedGame.gameResult!!.dragonsScore = 9
        modifiedGame.gameResult!!.opponentScore = 7
        modifiedGame.gameResult!!.gameID = 2
        modifiedGame.gameResult!!.overtimeLoss = false

        Assert.assertTrue(originalGame == modifiedGame)
    }
}
