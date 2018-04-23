package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * A player object.
 */
@Keep
public class Player implements Parcelable {

    public static final String FORWARD = "F";
    public static final String DEFENSE = "D";
    public static final String GOALIE = "G";
    public static final String PLAYER_NUMBER = "number";

    public String firstName;
    public String lastName;
    public int number;
    public int playerID;
    public String position;
    public String shot;
    public boolean injuredReserved;

    public Player() {
        //default constructor
    }

    public Player(String position) {
        this.position = position;
    }

    public boolean isForward() {
        return FORWARD.equalsIgnoreCase(position);
    }

    public boolean isDefense() {
        return DEFENSE.equalsIgnoreCase(position);
    }

    public boolean isGoalie() {
        return GOALIE.equalsIgnoreCase(position);
    }

    public PlayerPosition getPosition() {
        if (isGoalie()) {
            return PlayerPosition.GOALIE;
        } else if (isDefense()) {
            return PlayerPosition.DEFENSE;
        } else {
            return PlayerPosition.FORWARD;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeInt(this.number);
        dest.writeInt(this.playerID);
        dest.writeString(this.position);
        dest.writeString(this.shot);
        dest.writeByte(this.injuredReserved ? (byte) 1 : (byte) 0);
    }

    protected Player(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.number = in.readInt();
        this.playerID = in.readInt();
        this.position = in.readString();
        this.shot = in.readString();
        this.injuredReserved = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
