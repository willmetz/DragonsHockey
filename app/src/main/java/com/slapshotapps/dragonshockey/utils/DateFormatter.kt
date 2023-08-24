package com.slapshotapps.dragonshockey.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A date formatting utility class.
 */

object DateFormatter {
    private val ISO_8601_format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val GAME_TIME = SimpleDateFormat("K:mm a", Locale.US)

    fun getDateFromGameTime(gameTime: String): Date? {

        var gameDate: Date? = null
        try {
            gameDate = ISO_8601_format.parse(gameTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return gameDate
    }

    fun convertDateToGameTime(date: Date): String {
        return ISO_8601_format.format(date)
    }

    fun getGameTime(gameTime: Date): String {

        return GAME_TIME.format(gameTime)
    }

    fun getGameDateTime(gameTime: Date): String {

        val calendar = Calendar.getInstance()
        calendar.time = gameTime

        return (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
                + " "
                + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                + " "
                + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
                + " "
                + DateFormatter.getGameTime(gameTime))
    }

    fun getGameDate(gameTime: Date): String {

        val calendar = Calendar.getInstance()
        calendar.time = gameTime

        return (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
                + " "
                + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                + " "
                + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH)))
    }
}
