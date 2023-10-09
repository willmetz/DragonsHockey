package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

@Keep
class GameStats {

    @Exclude
    var key: String? = null

    var gameID: Int = 0

    @JvmField
    @PropertyName("stats")
    var gameStats: ArrayList<Stats>? = null

    @Keep
    class Stats {

        var playerID: Int = 0
        var assists: Int = 0
        var goals: Int = 0
        var present: Boolean = false
        var penaltyMinutes: Int = 0
        var goalsAgainst: Int = 0

        constructor() {
        }

        constructor(playerID: Int, assists: Int, goals: Int, penaltyMinutes: Int, present: Boolean,
                    goalsAgainst: Int) {
            this.playerID = playerID
            this.assists = assists
            this.present = present
            this.goals = goals
            this.penaltyMinutes = penaltyMinutes
            this.goalsAgainst = goalsAgainst
        }
    }

    fun getPlayerStats(playerID: Int): Stats {
        if (gameStats != null) {
            for (playerStats in gameStats!!) {
                if (playerStats.playerID == playerID) {
                    return playerStats
                }
            }
        }

        return Stats()
    }
}
