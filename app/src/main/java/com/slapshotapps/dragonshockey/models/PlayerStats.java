package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;


@Keep
public class PlayerStats implements Comparable<PlayerStats>,Parcelable {

    public int playerID;
    public String firstName;
    public String lastName;
    public int goals;
    public int assists;
    public int gamesPlayed;
    public int points;
    public int penaltyMinutes;

    public PlayerStats(int playerID, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerID = playerID;
    }

    @Override
    public int compareTo(@NonNull PlayerStats playerStats) {

        if (points < playerStats.points) {
            return 1;
        } else if (points > playerStats.points) {
            return -1;
        }
        return lastName.compareTo(playerStats.lastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.playerID);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeInt(this.goals);
        dest.writeInt(this.assists);
        dest.writeInt(this.gamesPlayed);
        dest.writeInt(this.points);
        dest.writeInt(this.penaltyMinutes);
    }

    protected PlayerStats(Parcel in) {
        this.playerID = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.goals = in.readInt();
        this.assists = in.readInt();
        this.gamesPlayed = in.readInt();
        this.points = in.readInt();
        this.penaltyMinutes = in.readInt();
    }

    public static final Parcelable.Creator<PlayerStats> CREATOR = new Parcelable.Creator<PlayerStats>() {
        @Override
        public PlayerStats createFromParcel(Parcel source) {
            return new PlayerStats(source);
        }

        @Override
        public PlayerStats[] newArray(int size) {
            return new PlayerStats[size];
        }
    };
}
