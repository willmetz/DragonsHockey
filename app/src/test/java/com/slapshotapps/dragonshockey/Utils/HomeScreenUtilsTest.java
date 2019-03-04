package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.GameResult;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by willmetz on 8/20/16.
 */

public class HomeScreenUtilsTest {

    @Test
    public void wasGameWin_Test() {

        Assert.assertFalse("Null test", HomeScreenUtils.wasWin(null));

        GameResult gameResult = new GameResult();
        gameResult.setDragonsScore(0);
        gameResult.setOpponentScore(1);
        gameResult.setOvertimeLoss(false);

        Assert.assertFalse("Loss test", HomeScreenUtils.wasWin(gameResult));

        gameResult.setDragonsScore(2);

        Assert.assertTrue("Win test", HomeScreenUtils.wasWin(gameResult));
    }

    @Test
    public void wasGameLoss_Test() {
        Assert.assertFalse("Null test", HomeScreenUtils.wasLoss(null));

        GameResult gameResult = new GameResult();
        gameResult.setDragonsScore(0);
        gameResult.setOpponentScore(1);
        gameResult.setOvertimeLoss(false);

        Assert.assertTrue("Loss test", HomeScreenUtils.wasLoss(gameResult));

        gameResult.setDragonsScore(2);

        Assert.assertFalse("Win test", HomeScreenUtils.wasLoss(gameResult));

        gameResult.setDragonsScore(0);
        gameResult.setOvertimeLoss(true);

        Assert.assertFalse("overtime loss test", HomeScreenUtils.wasLoss(gameResult));
    }

    @Test
    public void wasGameOvertimeLoss_Test() {

        Assert.assertFalse("Null test", HomeScreenUtils.wasOvertimeLoss(null));

        GameResult gameResult = new GameResult();
        gameResult.setDragonsScore(0);
        gameResult.setOpponentScore(1);
        gameResult.setOvertimeLoss(false);

        Assert.assertFalse("Loss test", HomeScreenUtils.wasOvertimeLoss(gameResult));

        gameResult.setOvertimeLoss(true);

        Assert.assertTrue("overtimeloss test", HomeScreenUtils.wasOvertimeLoss(gameResult));
    }

    @Test
    public void wasGameTie_Test() {

        Assert.assertFalse("Null test", HomeScreenUtils.wasTie(null));

        GameResult gameResult = new GameResult();
        gameResult.setDragonsScore(0);
        gameResult.setOpponentScore(1);
        gameResult.setOvertimeLoss(false);

        Assert.assertFalse("Loss test", HomeScreenUtils.wasTie(gameResult));

        gameResult.setDragonsScore(2);

        Assert.assertFalse("Win test", HomeScreenUtils.wasTie(gameResult));

        gameResult.setDragonsScore(1);

        Assert.assertTrue("tie test", HomeScreenUtils.wasTie(gameResult));
    }
}
