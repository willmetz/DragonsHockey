package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class PlayerStats implements Comparable<PlayerStats>, Parcelable {

    public final int playerID;
    public final String firstName;
    public final String lastName;
    public final PlayerPosition position;
    public int goals;
    public int assists;
    public int gamesPlayed;
    public int points;
    public int penaltyMinutes;
    public int goalsAgainst;
    public int shutouts;


    public PlayerStats(int playerID, String firstName, String lastName, PlayerPosition position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerID = playerID;
        this.position = position;
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
        dest.writeInt(this.position == null ? -1 : this.position.ordinal());
        dest.writeInt(this.goals);
        dest.writeInt(this.assists);
        dest.writeInt(this.gamesPlayed);
        dest.writeInt(this.points);
        dest.writeInt(this.penaltyMinutes);
        dest.writeInt(this.goalsAgainst);
        dest.writeInt(this.shutouts);
    }

    protected PlayerStats(Parcel in) {
        this.playerID = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        int tmpPosition = in.readInt();
        this.position = tmpPosition == -1 ? null : PlayerPosition.values()[tmpPosition];
        this.goals = in.readInt();
        this.assists = in.readInt();
        this.gamesPlayed = in.readInt();
        this.points = in.readInt();
        this.penaltyMinutes = in.readInt();
        this.goalsAgainst = in.readInt();
        this.shutouts = in.readInt();
    }

    public static final Creator<PlayerStats> CREATOR = new Creator<PlayerStats>() {
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
