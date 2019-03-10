package com.slapshotapps.dragonshockey.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
class PlayerStats(val playerID: Int, val firstName: String, val lastName: String, val position: PlayerPosition) :
        Comparable<PlayerStats>, Parcelable {
    var goals: Int = 0

    var assists: Int = 0

    var gamesPlayed: Int = 0

    var points: Int = 0

    var penaltyMinutes: Int = 0

    var goalsAgainst: Int = 0

    var shutouts: Int = 0

    override fun compareTo(other: PlayerStats): Int {

        if (points < other.points) {
            return 1
        } else if (points > other.points) {
            return -1
        }
        return lastName.compareTo(other.lastName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PlayerStats> = object : Parcelable.Creator<PlayerStats> {
            override fun createFromParcel(source: Parcel): PlayerStats = PlayerStats(source)
            override fun newArray(size: Int): Array<PlayerStats?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            PlayerPosition.values()[source.readInt()]
    ) {
        this.goals = source.readInt();
        this.assists = source.readInt();
        this.gamesPlayed = source.readInt();
        this.points = source.readInt();
        this.penaltyMinutes = source.readInt();
        this.goalsAgainst = source.readInt();
        this.shutouts = source.readInt();
    }


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(playerID)
        writeString(firstName)
        writeString(lastName)
        writeInt(position.ordinal)
        writeInt(goals)
        writeInt(assists)
        writeInt(gamesPlayed)
        writeInt(points)
        writeInt(penaltyMinutes)
        writeInt(goalsAgainst)
        writeInt(shutouts)
    }
}
