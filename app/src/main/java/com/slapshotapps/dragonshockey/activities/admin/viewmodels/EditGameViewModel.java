package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;

import java.util.Date;
import java.util.Locale;

/**
 * Created on 10/16/16.
 */

public class EditGameViewModel {
    private Game game;
    private Game originalGame;

    public EditGameViewModel(@NonNull Game game) {
        this.game = game;

        try {
            originalGame = game.clone();
        } catch (CloneNotSupportedException e) {
            originalGame = new Game();
        }
    }

    public String getGameID(Context context) {
        return context.getString(R.string.edit_game_game_id, game.gameID);
    }

    public String getOpponentName() {
        return game.opponent == null ? "" : game.opponent;
    }

    public void setOpponentName(String name) {
        game.opponent = name;
    }

    public void setOpponentScore(String score) {
        if (game.gameResult == null) {
            createGameResult();
        }

        if (score == null || score.isEmpty()) {
            game.gameResult.opponentScore = 0;
        } else {
            game.gameResult.opponentScore = Integer.valueOf(score);
        }
    }

    public String getOpponentScore() {
        if (game.gameResult != null) {
            return String.valueOf(game.gameResult.opponentScore);
        } else {
            return "";
        }
    }

    public void setDragonsScore(String score) {
        if (game.gameResult == null) {
            createGameResult();
        }

        if (score == null || score.isEmpty()) {
            game.gameResult.dragonsScore = 0;
        } else {
            game.gameResult.dragonsScore = Integer.valueOf(score);
        }
    }

    public String getDragonsScore() {
        if (game.gameResult != null) {
            return String.valueOf(game.gameResult.dragonsScore);
        } else {
            return "";
        }
    }

    public Date getGameDate() {
        return game.gameTimeToDate();
    }

    public void setGameDate(Date gameDate) {
        game.gameTime = DateFormaters.convertDateToGameTime(gameDate);
    }

    public String getGameDateAsString() {
        return DateFormaters.getGameDate(game.gameTimeToDate());
    }

    public String getGameTimeAsString() {
        return DateFormaters.getGameTime(game.gameTimeToDate());
    }

    public boolean hasChanged() {
        return !originalGame.equals(game);
    }

    public Game getGame() {
        return game;
    }

    public boolean getOTL() {
        return game.gameResult != null && game.gameResult.overtimeLoss;
    }

    public void setOTL(boolean isOTL) {
        if (game.gameResult == null) {
            createGameResult();
        }

        game.gameResult.overtimeLoss = isOTL;
    }

    private void createGameResult() {
        game.gameResult = new GameResult();
        game.gameResult.gameID = originalGame.gameID;
    }
}
