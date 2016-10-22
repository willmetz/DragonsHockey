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

    public EditGameViewModel(@NonNull Game game){
        this.game = game;
    }

    public String getGameID(Context context){
        return context.getString(R.string.edit_game_game_id, game.gameID);
    }

    public String getOpponentName(){
        return game.opponent;
    }

    public void setOpponentName(String name){
        game.opponent = name;
    }

    public void setOpponentScore(String score){
        if(game.gameResult==null){
            game.gameResult = new GameResult();
        }

        game.gameResult.opponentScore = Integer.valueOf(score);
    }

    public String getOpponentScore(){
        if(game.gameResult!=null){
            return String.valueOf(game.gameResult.opponentScore);
        }else{
            return "";
        }
    }

    public void setDragonsScore(String score){
        if(game.gameResult==null){
            game.gameResult = new GameResult();
        }

        game.gameResult.dragonsScore = Integer.valueOf(score);
    }

    public String getDragonsScore(){
        if(game.gameResult!=null){
            return String.valueOf(game.gameResult.dragonsScore);
        }else{
            return "";
        }
    }

    public Date getGameDate(){
        return game.gameTimeToDate();
    }

    public void setGameDate(Date gameDate){
        game.gameTime = DateFormaters.convertDateToGameTime(gameDate);
    }

    public String getGameDateAsString(){
        return DateFormaters.getGameDate(game.gameTimeToDate());
    }

    public String getGameTimeAsString(){
        return DateFormaters.getGameTime(game.gameTimeToDate());
    }

    public boolean matches(Game game){
        return this.game.equals(game);
    }

    public Game getGame(){
        return game;
    }
}
