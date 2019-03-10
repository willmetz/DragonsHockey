package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormatter;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.models.Game;

import androidx.annotation.NonNull;

/**
 * Created on 10/15/16.
 */

public class GameListItem extends ListItem {

    private Game game;

    public GameListItem(@NonNull Game game) {
        super(R.layout.view_admin_game_card);
        this.game = game;
    }

    public String getGameID() {
        return String.valueOf(game.getGameID());
    }

    public String getGameDate() {
        return DateFormatter.INSTANCE.getGameTime(game.gameTimeToDate());
    }

    public String getGameOpponent() {
        return game.getOpponent();
    }

    public String getGameResult() {
        return FormattingUtils.getGameScore(game.getGameResult(), game.getOpponent());
    }

    public Game getGame() {
        return game;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.GAME;
    }
}
