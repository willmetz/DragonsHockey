package com.slapshotapps.dragonshockey.activities.careerStats

import android.content.Context
import androidx.core.content.ContextCompat
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.models.PlayerStats
import java.util.*

class PlayerSeasonStatsVM {

    val seasonID: String
    var goals: Int = 0
    var assists: Int = 0
    var gamesPlayed: Int = 0
    var penaltyMinutes: Int = 0
    var goalsAgainst: Int = 0
    var shutouts: Int = 0

    val points: String
        get() = (goals + assists).toString()

    val goalsAgainstAverage: String
        get() = String.format(Locale.US, "%.2f", calcGAA())

    constructor(seasonID: String) {
        this.seasonID = seasonID
    }

    constructor(playerStats: PlayerStats, seasonID: String) {
        goals = playerStats.goals
        assists = playerStats.assists
        gamesPlayed = playerStats.gamesPlayed
        penaltyMinutes = playerStats.penaltyMinutes
        this.seasonID = seasonID
        goalsAgainst = playerStats.goalsAgainst
        shutouts = playerStats.shutouts
    }

    fun getGoalsAsString(): String {
        return goals.toString()
    }

    fun getAssistsAsString(): String {
        return assists.toString()
    }

    fun getGamesPlayedAsString(): String {
        return gamesPlayed.toString()
    }

    fun getPenaltyMinutesAsString(): String {
        return penaltyMinutes.toString()
    }

    fun getGoalsAgainstAsString(): String {
        return String.format(Locale.US, "%d", goalsAgainst)
    }

    fun getShutoutsAsString(): String {
        return shutouts.toString()
    }

    private fun calcGAA(): Float {
        return if (gamesPlayed == 0) {
            0f
        } else {
            goalsAgainst / gamesPlayed.toFloat()
        }
    }

    fun getBackgroundColor(context: Context): Int {
        return if (seasonID.equals("career", ignoreCase = true))
            ContextCompat.getColor(context,
                    R.color.lightGray)
        else
            ContextCompat.getColor(context, R.color.standardBackground)
    }
}
