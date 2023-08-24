package com.slapshotapps.dragonshockey.activities.stats.adapters

import com.slapshotapps.dragonshockey.utils.StatsUtils
import com.slapshotapps.dragonshockey.activities.stats.StatsListener
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.models.PlayerPosition
import com.slapshotapps.dragonshockey.models.PlayerStats
import java.util.*

class PlayerStatsVM(val playerStats: PlayerStats,
                    private val listener: PlayerStatsVMListener?) : Comparable<PlayerStatsVM>, StatsListener {
    private var sortSelection: StatSortSelection? = null

    val playerName: String
        get() = StatsUtils.fullPlayerName(playerStats)

    val lastName: String
        get() = playerStats.lastName!!

    val goals: String
        get() = playerStats.goals.toString()

    val assists: String
        get() = playerStats.assists.toString()

    val points: String
        get() = playerStats.points.toString()

    val penaltyMinutes: String
        get() = playerStats.penaltyMinutes.toString()

    val gamesPlayed: String
        get() = playerStats.gamesPlayed.toString()

    val position: PlayerPosition
        get() = playerStats.position

    interface PlayerStatsVMListener {
        fun onViewPLayerStats(playerStats: PlayerStats)
    }

    init {
        sortSelection = StatSortSelection.Points
    }

    fun setSortSelection(sortSelection: StatSortSelection) {
        this.sortSelection = sortSelection
    }

    fun position(): String {
        when (playerStats.position) {

            PlayerPosition.FORWARD -> return "(F)"
            PlayerPosition.DEFENSE -> return "(D)"
            PlayerPosition.GOALIE -> return "(G)"
            else -> return "(F)"
        }
    }

    fun goalsAgainst(): String {
        return String.format(Locale.US, "%d", playerStats.goalsAgainst)
    }

    fun goalsAgainstAverage(): String {
        return String.format(Locale.US, "%.2f", calcGoalsAgainstAverage())
    }

    fun shutouts(): String {
        return String.format(Locale.US, "%d", playerStats.shutouts)
    }

    private fun calcGoalsAgainstAverage(): Float {
        return if (playerStats.gamesPlayed > 0) {
            playerStats.goalsAgainst / playerStats.gamesPlayed.toFloat()
        } else {
            0f
        }
    }

    override fun compareTo(other: PlayerStatsVM): Int {

        when (sortSelection) {
            StatSortSelection.Goals -> return sortByGoals(other)
            StatSortSelection.Assists -> return sortByAssists(other)
            StatSortSelection.GamesPlayed -> return sortByGamesPlayed(other)
            StatSortSelection.PenaltyMinutes -> return sortByPenaltyMinutes(other)
            StatSortSelection.Points -> return sortByPoints(other)
            else -> return sortByPoints(other)
        }
    }

    private fun sortByPoints(playerStatsVM: PlayerStatsVM): Int {
        if (playerStats.position == PlayerPosition.GOALIE) {
            return 1
        } else if (playerStatsVM.position == PlayerPosition.GOALIE) {
            return -1
        } else if (playerStats.points < Integer.valueOf(playerStatsVM.points)) {
            return 1
        } else if (playerStats.points > Integer.valueOf(playerStatsVM.points)) {
            return -1
        }
        return playerStats.lastName!!.compareTo(playerStatsVM.lastName)
    }

    private fun sortByGoals(playerStatsVM: PlayerStatsVM): Int {

        if (playerStats.position == PlayerPosition.GOALIE) {
            return 1
        } else if (playerStatsVM.position == PlayerPosition.GOALIE) {
            return -1
        } else if (playerStats.goals < Integer.valueOf(playerStatsVM.goals)) {
            return 1
        } else if (playerStats.goals > Integer.valueOf(playerStatsVM.goals)) {
            return -1
        }
        return playerStats.lastName!!.compareTo(playerStatsVM.lastName)
    }

    private fun sortByAssists(playerStatsVM: PlayerStatsVM): Int {
        if (playerStats.position == PlayerPosition.GOALIE) {
            return 1
        } else if (playerStatsVM.position == PlayerPosition.GOALIE) {
            return -1
        } else if (playerStats.assists < Integer.valueOf(playerStatsVM.assists)) {
            return 1
        } else if (playerStats.assists > Integer.valueOf(playerStatsVM.assists)) {
            return -1
        }
        return playerStats.lastName!!.compareTo(playerStatsVM.lastName)
    }

    private fun sortByGamesPlayed(playerStatsVM: PlayerStatsVM): Int {
        if (playerStats.gamesPlayed < Integer.valueOf(playerStatsVM.gamesPlayed)) {
            return 1
        } else if (playerStats.gamesPlayed > Integer.valueOf(playerStatsVM.gamesPlayed)) {
            return -1
        }
        return playerStats.lastName!!.compareTo(playerStatsVM.lastName)
    }

    private fun sortByPenaltyMinutes(playerStatsVM: PlayerStatsVM): Int {
        if (playerStats.penaltyMinutes < Integer.valueOf(playerStatsVM.penaltyMinutes)) {
            return 1
        } else if (playerStats.penaltyMinutes > Integer.valueOf(
                        playerStatsVM.penaltyMinutes)) {
            return -1
        }
        return playerStats.lastName!!.compareTo(playerStatsVM.lastName)
    }

    override fun onStatsClicked() {
        listener?.onViewPLayerStats(playerStats)
    }
}
