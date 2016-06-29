package com.slapshotapps.dragonshockey.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A date formatting utility class.
 */

public class DateFormaters {
  private static final SimpleDateFormat ISO_8601_format =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final SimpleDateFormat GAME_TIME = new SimpleDateFormat("K:mm a");

  public static Date getDateFromGameTime(String gameTime) {

    Date gameDate = null;
    try {
      gameDate = ISO_8601_format.parse(gameTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return gameDate;
  }

  public static String getGameTime(Date gameTime) {

    return GAME_TIME.format(gameTime);
  }
}
