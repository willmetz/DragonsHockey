package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.GameResult;

/**
 * Created by willmetz on 6/2/16.
 */

public class FormattingUtils {

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

      gameScore = gameResult.dragonsScore + "-" +
          gameResult.opponentScore +
          " (" + didDragonsWin(gameResult) + ")";
    }

    return gameScore;
  }

  public static String didDragonsWin(GameResult gameResult) {
    String winLossTie = "N/A";

    if (gameResult != null) {
      if (gameResult.dragonsScore > gameResult.opponentScore) {
        winLossTie = "W";
      } else if (gameResult.opponentScore > gameResult.dragonsScore) {
        winLossTie = "L";
      } else {
        winLossTie = "T";
      }
    }

    return winLossTie;
  }
}
