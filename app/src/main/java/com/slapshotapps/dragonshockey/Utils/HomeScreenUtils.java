package com.slapshotapps.dragonshockey.Utils;

import androidx.annotation.Nullable;
import com.slapshotapps.dragonshockey.models.GameResult;

/**
 * Created by willmetz on 8/20/16.
 */

public class HomeScreenUtils {
    public static boolean wasWin(@Nullable GameResult gameResult) {
        if (gameResult != null) {
            return gameResult.getDragonsScore() > gameResult.getOpponentScore();
        }

        return false;
    }

    public static boolean wasOvertimeLoss(@Nullable GameResult gameResult) {
        return gameResult != null && gameResult.getOvertimeLoss();
    }

    public static boolean wasLoss(@Nullable GameResult gameResult) {
        if (gameResult != null && !wasOvertimeLoss(gameResult)) {
            return gameResult.getDragonsScore() < gameResult.getOpponentScore();
        }

        return false;
    }

    public static boolean wasTie(@Nullable GameResult gameResult) {
        return gameResult != null && gameResult.getDragonsScore() == gameResult.getOpponentScore();
    }
}
