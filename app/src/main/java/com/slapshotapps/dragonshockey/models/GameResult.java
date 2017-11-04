package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * A custom object for a game result
 */
@Keep
public class GameResult implements Parcelable {
    public int gameID;
    public int dragonsScore;
    public int opponentScore;
    public boolean overtimeLoss;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gameID);
        dest.writeInt(this.dragonsScore);
        dest.writeInt(this.opponentScore);
        dest.writeByte(this.overtimeLoss ? (byte) 1 : (byte) 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameResult that = (GameResult) o;

        if (gameID != that.gameID) {
            return false;
        }
        if (dragonsScore != that.dragonsScore) {
            return false;
        }
        if (opponentScore != that.opponentScore) {
            return false;
        }
        return overtimeLoss == that.overtimeLoss;
    }

    @Override
    public int hashCode() {
        int result = gameID;
        result = 31 * result + dragonsScore;
        result = 31 * result + opponentScore;
        result = 31 * result + (overtimeLoss ? 1 : 0);
        return result;
    }

    public GameResult() {
    }

    protected GameResult(Parcel in) {
        this.gameID = in.readInt();
        this.dragonsScore = in.readInt();
        this.opponentScore = in.readInt();
        this.overtimeLoss = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GameResult> CREATOR =
        new Parcelable.Creator<GameResult>() {
            @Override
            public GameResult createFromParcel(Parcel source) {
                return new GameResult(source);
            }

            @Override
            public GameResult[] newArray(int size) {
                return new GameResult[size];
            }
        };
}
