package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.Game;

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

    public int getOpponentScore(){
        if(game.gameResult!=null){
            return game.gameResult.opponentScore;
        }else{
            return 0;
        }
    }

    public int getDragonsScore(){
        if(game.gameResult!=null){
            return game.gameResult.dragonsScore;
        }else{
            return 0;
        }
    }
}
