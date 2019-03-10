package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep

/**
 * Created on 10/30/16.
 */
@Keep
class GameUpdateKeys(private val gameKey: String?, private val gameResultKey: String?,
                     private val gameStatsKey: String?) {

    fun getGameResultKey(): String {
        return gameResultKey.toString()
    }

    fun getGameKey(): String {
        return gameKey.toString()
    }

    fun getGameStatsKey(): String {
        return gameStatsKey.toString()
    }

    fun gameResultKeyValid(): Boolean {
        return gameResultKey != null
    }

    fun gameKeyValid(): Boolean {
        return gameKey != null
    }

    fun gameStatsKeyValid(): Boolean {
        return gameStatsKey != null
    }
}
