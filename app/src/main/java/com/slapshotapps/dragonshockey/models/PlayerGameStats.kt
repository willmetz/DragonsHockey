package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep
import java.util.*

/**
 * Created on 11/5/16.
 */
@Keep
class PlayerGameStats {

  var playerStatsKey: String? = null
  var players: ArrayList<Player>? = null
  var playerGameStats: GameStats? = null

  val isKeyValid: Boolean
    get() = playerStatsKey != null
}
