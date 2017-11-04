package com.slapshotapps.dragonshockey.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return gameDate;
    }

    public static String convertDateToGameTime(Date date) {
        return ISO_8601_format.format(date);
    }

    public static String getGameTime(Date gameTime) {

        return GAME_TIME.format(gameTime);
    }

    public static String getGameDateTime(Date gameTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gameTime);

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
            + " "
            + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
            + " "
            + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
            + " "
            + DateFormaters.getGameTime(gameTime);
    }

    public static String getGameDate(Date gameTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gameTime);

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
            + " "
            + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
            + " "
            + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH));
    }
}
