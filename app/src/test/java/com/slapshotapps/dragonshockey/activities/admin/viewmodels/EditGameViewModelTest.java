package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 10/23/16.
 */
public class EditGameViewModelTest {
    @Test
    public void getOpponentName_null() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);

        Assert.assertNotNull(editGameViewModel.getOpponentName());
    }

    @Test
    public void setOpponentName_null() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setOpponentName("test");

        Assert.assertEquals("test", editGameViewModel.getOpponentName());

        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentName_notNull() {
        Game game = new Game();
        game.opponent = "beforeTest";

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setOpponentName("test");

        Assert.assertEquals("test", editGameViewModel.getOpponentName());

        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_null() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setOpponentScore(null);

        Assert.assertEquals("0", editGameViewModel.getOpponentScore());
        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_nullGameResult() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setOpponentScore("8");

        Assert.assertEquals("8", editGameViewModel.getOpponentScore());
        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_notNull() {
        Game game = new Game();
        game.gameResult = new GameResult();
        game.gameResult.opponentScore = 2;

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setOpponentScore("8");

        Assert.assertEquals("8", editGameViewModel.getOpponentScore());
        Assert.assertTrue(editGameViewModel.hasChanged());

    }

    @Test
    public void getOpponentScore() throws Exception {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);

        Assert.assertEquals("", editGameViewModel.getOpponentScore());
        Assert.assertFalse(editGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_null() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setDragonsScore(null);

        Assert.assertEquals("0", editGameViewModel.getDragonsScore());
        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_nullGameResult() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setDragonsScore("8");

        Assert.assertEquals("8", editGameViewModel.getDragonsScore());
        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_notNull() {
        Game game = new Game();
        game.gameResult = new GameResult();
        game.gameResult.dragonsScore = 99;

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);
        editGameViewModel.setDragonsScore("3");

        Assert.assertEquals("3", editGameViewModel.getDragonsScore());
        Assert.assertTrue(editGameViewModel.hasChanged());
    }

    @Test
    public void getDragonsScore() {
        Game game = new Game();

        EditGameViewModel editGameViewModel = new EditGameViewModel(game);

        Assert.assertEquals("", editGameViewModel.getDragonsScore());
        Assert.assertFalse(editGameViewModel.hasChanged());
    }


}