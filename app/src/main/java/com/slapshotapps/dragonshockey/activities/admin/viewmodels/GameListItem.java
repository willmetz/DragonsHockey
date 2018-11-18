package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import androidx.annotation.NonNull;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.models.Game;

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
        return String.valueOf(game.gameID);
    }

    public String getGameDate() {
        return DateFormaters.getGameTime(game.gameTimeToDate());
    }

    public String getGameOpponent() {
        return game.opponent;
    }

    public String getGameResult() {
        return FormattingUtils.getGameScore(game.gameResult, game.opponent);
    }

    public Game getGame() {
        return game;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.GAME;
    }
}
