package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import android.content.Context;
import androidx.annotation.NonNull;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import java.util.Date;

/**
 * Created on 10/16/16.
 */

public class AdminGameViewModel {
    private Game game;
    private Game originalGame;

    public AdminGameViewModel(@NonNull Game game) {
        this.game = game;

        try {
            originalGame = game.clone();
        }
        catch (CloneNotSupportedException e) {
            originalGame = new Game();
        }
    }

    public String getGameID(Context context) {
        return context.getString(R.string.edit_game_game_id, game.getGameID());
    }

    public String getOpponentName() {
        return game.getOpponent() == null ? "" : game.getOpponent();
    }

    public void setOpponentName(String name) {
        game.setOpponent(name);
    }

    public void setOpponentScore(String score) {
        if (game.getGameResult() == null) {
            createGameResult();
        }

        if (score == null || score.isEmpty()) {
            game.getGameResult().opponentScore = 0;
        } else {
            game.getGameResult().opponentScore = Integer.valueOf(score);
        }
    }

    public String getOpponentScore() {
        if (game.getGameResult() != null) {
            return String.valueOf(game.getGameResult().opponentScore);
        } else {
            return "";
        }
    }

    public void setDragonsScore(String score) {
        if (game.getGameResult() == null) {
            createGameResult();
        }

        if (score == null || score.isEmpty()) {
            game.getGameResult().dragonsScore = 0;
        } else {
            game.getGameResult().dragonsScore = Integer.valueOf(score);
        }
    }

    public String getDragonsScore() {
        if (game.getGameResult() != null) {
            return String.valueOf(game.getGameResult().dragonsScore);
        } else {
            return "";
        }
    }

    public Date getGameDate() {
        return game.gameTimeToDate();
    }

    public void setGameDate(Date gameDate) {
        game.setGameTime(DateFormaters.convertDateToGameTime(gameDate));
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
        return game.getGameResult() != null && game.getGameResult().overtimeLoss;
    }

    public void setOTL(boolean isOTL) {
        if (game.getGameResult() == null) {
            createGameResult();
        }

        game.getGameResult().overtimeLoss = isOTL;
    }

    private void createGameResult() {
        game.setGameResult(new GameResult());
        game.getGameResult().gameID = originalGame.getGameID();
    }
}
