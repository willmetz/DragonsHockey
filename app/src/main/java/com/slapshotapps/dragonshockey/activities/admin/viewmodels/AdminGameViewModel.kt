package com.slapshotapps.dragonshockey.activities.admin.viewmodels

import android.content.Context
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.models.GameResult
import com.slapshotapps.dragonshockey.utils.DateFormatter.convertDateToGameTime
import com.slapshotapps.dragonshockey.utils.DateFormatter.getGameDate
import com.slapshotapps.dragonshockey.utils.DateFormatter.getGameTime
import java.util.Date

/**
 * Created on 10/16/16.
 */
class AdminGameViewModel(val game: Game) {
    private var originalGame: Game? = null

    init {
        originalGame = try {
            game.clone()
        } catch (e: CloneNotSupportedException) {
            Game()
        }
    }

    fun getGameID(context: Context): String {
        return context.getString(R.string.edit_game_game_id, game.gameID)
    }

    var opponentName: String?
        get() = if (game.opponent == null) "" else game.opponent
        set(name) {
            game.opponent = name
        }
    var opponentScore: String?
        get() = if (game.gameResult != null) {
            game.gameResult!!.opponentScore.toString()
        } else {
            ""
        }
        set(score) {
            if (game.gameResult == null) {
                createGameResult()
            }
            if (score == null || score.isEmpty()) {
                game.gameResult!!.opponentScore = 0
            } else {
                game.gameResult!!.opponentScore = Integer.valueOf(score)
            }
        }
    var dragonsScore: String?
        get() = if (game.gameResult != null) {
            game.gameResult!!.dragonsScore.toString()
        } else {
            ""
        }
        set(score) {
            if (game.gameResult == null) {
                createGameResult()
            }
            if (score == null || score.isEmpty()) {
                game.gameResult!!.dragonsScore = 0
            } else {
                game.gameResult!!.dragonsScore = Integer.valueOf(score)
            }
        }
    var gameDate: Date?
        get() = game.gameTimeToDate()
        set(gameDate) {
            game.gameTime = convertDateToGameTime(gameDate!!)
        }
    val gameDateAsString: String
        get() = getGameDate(game.gameTimeToDate()!!)
    val gameTimeAsString: String
        get() = getGameTime(game.gameTimeToDate()!!)

    fun hasChanged(): Boolean {
        return !originalGame!!.equals(game)
    }

    var oTL: Boolean
        get() = game.gameResult != null && game.gameResult!!.overtimeLoss
        set(isOTL) {
            if (game.gameResult == null) {
                createGameResult()
            }
            game.gameResult!!.overtimeLoss = isOTL
        }

    private fun createGameResult() {
        game.gameResult = GameResult()
        game.gameResult!!.gameID = originalGame!!.gameID
    }
}