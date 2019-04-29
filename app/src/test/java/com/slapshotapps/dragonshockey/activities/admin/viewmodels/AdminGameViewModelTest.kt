package com.slapshotapps.dragonshockey.activities.admin.viewmodels

import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.models.GameResult
import junit.framework.Assert
import org.junit.Test

/**
 * Created on 10/23/16.
 */
class AdminGameViewModelTest {
    @Test
    fun getOpponentName_null() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)

        Assert.assertNotNull(adminGameViewModel.opponentName)
    }

    @Test
    fun setOpponentName_null() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.opponentName = "test"

        Assert.assertEquals("test", adminGameViewModel.opponentName)

        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setOpponentName_notNull() {
        val game = Game()
        game.opponent = "beforeTest"

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.opponentName = "test"

        Assert.assertEquals("test", adminGameViewModel.opponentName)

        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setOpponentScore_null() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.opponentScore = null

        Assert.assertEquals("0", adminGameViewModel.opponentScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setOpponentScore_nullGameResult() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.opponentScore = "8"

        Assert.assertEquals("8", adminGameViewModel.opponentScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setOpponentScore_notNull() {
        val game = Game()
        game.gameResult = GameResult()
        game.gameResult!!.opponentScore = 2

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.opponentScore = "8"

        Assert.assertEquals("8", adminGameViewModel.opponentScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    @Throws(Exception::class)
    fun getOpponentScore() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)

        Assert.assertEquals("", adminGameViewModel.opponentScore)
        Assert.assertFalse(adminGameViewModel.hasChanged())
    }

    @Test
    fun setDragonsScore_null() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.dragonsScore = null

        Assert.assertEquals("0", adminGameViewModel.dragonsScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setDragonsScore_nullGameResult() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.dragonsScore = "8"

        Assert.assertEquals("8", adminGameViewModel.dragonsScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun setDragonsScore_notNull() {
        val game = Game()
        game.gameResult = GameResult()
        game.gameResult!!.dragonsScore = 99

        val adminGameViewModel = AdminGameViewModel(game)
        adminGameViewModel.dragonsScore = "3"

        Assert.assertEquals("3", adminGameViewModel.dragonsScore)
        Assert.assertTrue(adminGameViewModel.hasChanged())
    }

    @Test
    fun getDragonsScore() {
        val game = Game()

        val adminGameViewModel = AdminGameViewModel(game)

        Assert.assertEquals("", adminGameViewModel.dragonsScore)
        Assert.assertFalse(adminGameViewModel.hasChanged())
    }
}