package com.slapshotapps.dragonshockey.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep


/**
 * A player object.
 */
@Keep
class Player : Parcelable {
    var firstName: String? = null

    var lastName: String? = null

    var number: Int = 0

    var playerID: Int = 0

    var position: String? = null

    var shot: String? = null

    var injuredReserved: Boolean = false

    val isForward: Boolean
        get() = FORWARD.equals(position, ignoreCase = true)

    val isDefense: Boolean
        get() = DEFENSE.equals(position, ignoreCase = true)

    val isGoalie: Boolean
        get() = GOALIE.equals(position, ignoreCase = true)

    constructor() {
        //default constructor
    }

    constructor(position: String) {
        this.position = position
    }

    fun getPlayerPosition(): PlayerPosition {
        return if (isGoalie) {
            PlayerPosition.GOALIE
        } else if (isDefense) {
            PlayerPosition.DEFENSE
        } else {
            PlayerPosition.FORWARD
        }
    }

    companion object {
        val FORWARD = "F"

        val DEFENSE = "D"

        val GOALIE = "G"

        val PLAYER_NUMBER = "number"

        @JvmField
        val CREATOR: Parcelable.Creator<Player> = object : Parcelable.Creator<Player> {
            override fun createFromParcel(source: Parcel): Player = Player(source)
            override fun newArray(size: Int): Array<Player?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this() {
        firstName = source.readString()
        lastName = source.readString()
        number = source.readInt()
        playerID = source.readInt()
        position = source.readString()
        shot = source.readString()
        injuredReserved = source.readByte().toInt() != 0
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        dest.writeString(firstName)
        dest.writeString(lastName)
        dest.writeInt(number)
        dest.writeInt(playerID)
        dest.writeString(position)
        dest.writeString(shot)
        dest.writeByte(if (injuredReserved) 1.toByte() else 0.toByte())
    }
}
