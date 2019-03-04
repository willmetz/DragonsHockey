package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.GameResult;

/**
 * Created by willmetz on 6/2/16.
 */

public class FormattingUtils {

    public static final String OVERTIME_LOSS = "OTL";
    public static final String WIN = "W";
    public static final String LOSS = "L";
    public static final String TIE = "T";

    /**
     * Will return the numerical value with appropriate suffix
     *
     * @param value integer value to add a suffix to (should be < 100)
     * @return value with suffix or empty string
     */
    public static String getValueWithSuffix(int value) {

        int lastDigit = value % 10;
        String formatted = String.valueOf(value);

        if (value == 11 || value == 12 || value == 13 || lastDigit > 3 || lastDigit == 0) {
            formatted = value + "th";
        } else if (lastDigit == 1) {
            formatted = value + "st";
        } else if (lastDigit == 2) {
            formatted = value + "nd";
        } else if (lastDigit == 3) {
            formatted = value + "rd";
        }

        return formatted;
    }

    public static String getGameScore(GameResult gameResult, String opponent) {
        String gameScore = "TBD";

        if (gameResult != null) {

            gameScore = gameResult.getDragonsScore()
                + "-"
                + gameResult.getOpponentScore()
                + " ("
                + getGameResultAsString(gameResult)
                + ")";
        }

        return gameScore;
    }

    public static String getGameResultAsString(GameResult gameResult) {
        String gameResultText = "N/A";

        if (gameResult != null) {
            if (gameResult.getOvertimeLoss()) {
                gameResultText = OVERTIME_LOSS;
            } else if (gameResult.getDragonsScore() > gameResult.getOpponentScore()) {
                gameResultText = WIN;
            } else if (gameResult.getOpponentScore() > gameResult.getDragonsScore()) {
                gameResultText = LOSS;
            } else {
                gameResultText = TIE;
            }
        }

        return gameResultText;
    }
}
