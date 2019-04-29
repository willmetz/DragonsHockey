package com.slapshotapps.dragonshockey.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

/**
 * A custom object for a game result
 */
@Keep
class GameResult : Parcelable {
    var gameID: Int = 0

    var dragonsScore: Int = 0

    var opponentScore: Int = 0

    var overtimeLoss: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || !(other is GameResult)) {
            return false
        }

        val that = other as GameResult?

        if (gameID != that!!.gameID) {
            return false
        }
        if (dragonsScore != that.dragonsScore) {
            return false
        }
        return if (opponentScore != that.opponentScore) {
            false
        } else overtimeLoss == that.overtimeLoss
    }

    override fun hashCode(): Int {
        var result = gameID
        result = 31 * result + dragonsScore
        result = 31 * result + opponentScore
        result = 31 * result + if (overtimeLoss) 1 else 0
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GameResult> = object : Parcelable.Creator<GameResult> {
            override fun createFromParcel(source: Parcel): GameResult = GameResult(source)
            override fun newArray(size: Int): Array<GameResult?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this() {
        this.gameID = source.readInt();
        this.dragonsScore = source.readInt();
        this.opponentScore = source.readInt();
        this.overtimeLoss = 1 == source.readInt();
    }


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(gameID)
        writeInt(dragonsScore)
        writeInt(opponentScore)
        writeInt((if (overtimeLoss) 1 else 0))
    }
}
