package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep
import java.util.*

/**
 * A class that will hold the schedule for the season
 */
@Keep
class SeasonSchedule {
    var games: MutableList<Game> = ArrayList()

    val allGames: List<Game>
        get() = games

    fun addGame(game: Game) {
        games.add(game)
    }

    fun numberOfGames(): Int {
        return games.size
    }

    fun getGame(gameID: Int): Game? {
        return games.first {
            gameID == it.gameID
        }
    }
}
