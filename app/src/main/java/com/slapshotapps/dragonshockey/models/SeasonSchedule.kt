package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep
import java.util.*

/**
 * A class that will hold the schedule for the season
 */
@Keep
class SeasonSchedule {
  var games: MutableList<Game>

  val allGames: List<Game>
    get() = games

  init {
    games = ArrayList()
  }

  fun addGame(game: Game) {
    games.add(game)
  }

  fun numberOfGames(): Int {
    return games.size
  }

  fun getGame(gameID: Int): Game? {
    for (game in games) {
      if (game.gameID == gameID) {
        return game
      }
    }

    return null
  }
}
