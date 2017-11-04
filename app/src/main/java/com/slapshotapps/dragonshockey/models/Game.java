package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import com.google.firebase.database.Exclude;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import java.util.Date;

/**
 * A custom object for a game
 */
@Keep
public class Game implements Parcelable, Cloneable {
    public String gameTime;
    public String opponent;
    public int gameID;
    public boolean home;

    @Exclude
    public GameResult gameResult;

    public Date gameTimeToDate() {
        if (gameTime == null) {
            return null;
        }

        return DateFormaters.getDateFromGameTime(gameTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Game game = (Game) o;

        if (gameID != game.gameID) {
            return false;
        }
        if (gameTime != null ? !gameTime.equals(game.gameTime) : game.gameTime != null) {
            return false;
        }
        if (opponent != null ? !opponent.equals(game.opponent) : game.opponent != null) {
            return false;
        }
        return gameResult != null ? gameResult.equals(game.gameResult) : game.gameResult == null;
    }

    @Override
    public Game clone() throws CloneNotSupportedException {
        Game cloneGame = new Game();
        cloneGame.gameTime = gameTime;
        cloneGame.opponent = opponent;
        cloneGame.gameID = gameID;
        cloneGame.home = home;
        if (gameResult != null) {
            cloneGame.gameResult = new GameResult();
            cloneGame.gameResult.dragonsScore = gameResult.dragonsScore;
            cloneGame.gameResult.opponentScore = gameResult.opponentScore;
            cloneGame.gameResult.overtimeLoss = gameResult.overtimeLoss;
            cloneGame.gameResult.gameID = gameResult.gameID;
        }
        return cloneGame;
    }

    @Override
    public int hashCode() {
        int result = gameTime != null ? gameTime.hashCode() : 0;
        result = 31 * result + (opponent != null ? opponent.hashCode() : 0);
        result = 31 * result + gameID;
        result = 31 * result + (gameResult != null ? gameResult.hashCode() : 0);
        result = 31 * result + (home ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gameTime);
        dest.writeString(this.opponent);
        dest.writeInt(this.gameID);
        dest.writeParcelable(this.gameResult, flags);
        dest.writeByte((byte) (home ? 1 : 0));
    }

    public Game() {
    }

    protected Game(Parcel in) {
        this.gameTime = in.readString();
        this.opponent = in.readString();
        this.gameID = in.readInt();
        this.gameResult = in.readParcelable(GameResult.class.getClassLoader());
        this.home = in.readByte() == 1;
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
