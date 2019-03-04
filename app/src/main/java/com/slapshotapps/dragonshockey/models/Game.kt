package com.slapshotapps.dragonshockey.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import com.slapshotapps.dragonshockey.Utils.DateFormaters
import java.util.*

/**
 * A custom object for a game
 */
@Keep
class Game : Cloneable, Parcelable {
  var gameTime: String? = null

  var opponent: String? = null

  var gameID: Int = 0

  var home: Boolean = false

  @Exclude
  var gameResult: GameResult? = null

  fun gameTimeToDate(): Date? {
    return if (gameTime == null) {
      null
    } else DateFormaters.getDateFromGameTime(gameTime)

  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || !(other is Game)) {
      return false
    }

    val game = other as Game?

    if (gameID != game!!.gameID) {
      return false
    }
    if (if (gameTime != null) gameTime != game.gameTime else game.gameTime != null) {
      return false
    }
    if (if (opponent != null) opponent != game.opponent else game.opponent != null) {
      return false
    }
    return if (gameResult != null) gameResult == game.gameResult else game.gameResult == null
  }

  @Throws(CloneNotSupportedException::class)
  public override fun clone(): Game {
    val cloneGame = Game()
    cloneGame.gameTime = gameTime
    cloneGame.opponent = opponent
    cloneGame.gameID = gameID
    cloneGame.home = home
    if (gameResult != null) {
      cloneGame.gameResult = GameResult()
      cloneGame.gameResult!!.dragonsScore = gameResult!!.dragonsScore
      cloneGame.gameResult!!.opponentScore = gameResult!!.opponentScore
      cloneGame.gameResult!!.overtimeLoss = gameResult!!.overtimeLoss
      cloneGame.gameResult!!.gameID = gameResult!!.gameID
    }
    return cloneGame
  }

  override fun hashCode(): Int {
    var result = if (gameTime != null) gameTime!!.hashCode() else 0
    result = 31 * result + if (opponent != null) opponent!!.hashCode() else 0
    result = 31 * result + gameID
    result = 31 * result + if (gameResult != null) gameResult!!.hashCode() else 0
    result = 31 * result + if (home) 1 else 0
    return result
  }

  constructor()

  constructor(source: Parcel) : this() {
    gameTime = source.readString()
    opponent = source.readString()
    gameID = source.readInt()
    gameResult = source.readParcelable(GameResult::class.java.classLoader)
    home = source.readByte().toInt() == 1
  }

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(gameTime)
    writeString(opponent)
    writeInt(gameID)
    writeParcelable(gameResult, flags)
    writeByte(if (home) 1 else 0)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Game> = object : Parcelable.Creator<Game> {
      override fun createFromParcel(source: Parcel): Game = Game(source)
      override fun newArray(size: Int): Array<Game?> = arrayOfNulls(size)
    }
  }
}
