package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.slapshotapps.dragonshockey.Utils.DateFormaters;

import java.util.Date;

/**
 * A custom object for a game
 */

public class Game implements Parcelable {
  public String gameTime;
  public String opponent;
  public int gameID;
  public GameResult gameResult;

  public Date gameTimeToDate() {
    if (gameTime == null) {
      return null;
    }

    return DateFormaters.getDateFromGameTime(gameTime);
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
  }

  public Game() {
  }

  protected Game(Parcel in) {
    this.gameTime = in.readString();
    this.opponent = in.readString();
    this.gameID = in.readInt();
    this.gameResult = in.readParcelable(GameResult.class.getClassLoader());
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
