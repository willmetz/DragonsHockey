package com.slapshotapps.dragonshockey.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A custom object for a game result
 */

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

  public GameResult() {
  }

  protected GameResult(Parcel in) {
    this.gameID = in.readInt();
    this.dragonsScore = in.readInt();
    this.opponentScore = in.readInt();
    this.overtimeLoss = in.readByte() != 0;
  }

  public static final Parcelable.Creator<GameResult> CREATOR = new Parcelable.Creator<GameResult>() {
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
