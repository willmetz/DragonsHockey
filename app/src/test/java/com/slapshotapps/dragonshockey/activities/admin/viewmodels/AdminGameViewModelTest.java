package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created on 10/23/16.
 */
public class AdminGameViewModelTest {
    @Test
    public void getOpponentName_null() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);

        Assert.assertNotNull(adminGameViewModel.getOpponentName());
    }

    @Test
    public void setOpponentName_null() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setOpponentName("test");

        Assert.assertEquals("test", adminGameViewModel.getOpponentName());

        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentName_notNull() {
        Game game = new Game();
        game.opponent = "beforeTest";

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setOpponentName("test");

        Assert.assertEquals("test", adminGameViewModel.getOpponentName());

        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_null() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setOpponentScore(null);

        Assert.assertEquals("0", adminGameViewModel.getOpponentScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_nullGameResult() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setOpponentScore("8");

        Assert.assertEquals("8", adminGameViewModel.getOpponentScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setOpponentScore_notNull() {
        Game game = new Game();
        game.gameResult = new GameResult();
        game.gameResult.opponentScore = 2;

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setOpponentScore("8");

        Assert.assertEquals("8", adminGameViewModel.getOpponentScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void getOpponentScore() throws Exception {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);

        Assert.assertEquals("", adminGameViewModel.getOpponentScore());
        Assert.assertFalse(adminGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_null() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setDragonsScore(null);

        Assert.assertEquals("0", adminGameViewModel.getDragonsScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_nullGameResult() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setDragonsScore("8");

        Assert.assertEquals("8", adminGameViewModel.getDragonsScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void setDragonsScore_notNull() {
        Game game = new Game();
        game.gameResult = new GameResult();
        game.gameResult.dragonsScore = 99;

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);
        adminGameViewModel.setDragonsScore("3");

        Assert.assertEquals("3", adminGameViewModel.getDragonsScore());
        Assert.assertTrue(adminGameViewModel.hasChanged());
    }

    @Test
    public void getDragonsScore() {
        Game game = new Game();

        AdminGameViewModel adminGameViewModel = new AdminGameViewModel(game);

        Assert.assertEquals("", adminGameViewModel.getDragonsScore());
        Assert.assertFalse(adminGameViewModel.hasChanged());
    }
}