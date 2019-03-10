package com.slapshotapps.dragonshockey.Utils;

import android.content.Context;
import android.content.Intent;

import com.slapshotapps.dragonshockey.activities.admin.AdminActivity;
import com.slapshotapps.dragonshockey.activities.admin.AdminAuthActivity;
import com.slapshotapps.dragonshockey.activities.admin.EditGameActivity;
import com.slapshotapps.dragonshockey.activities.admin.EditStatsActivity;
import com.slapshotapps.dragonshockey.models.Game;

public class DragonsHockeyIntents {

    public static final String EXTRA_GAME = "com.slapshotapps.dragonshockey.extragame";
    public static final String EXTRA_GAME_ID = "com.slapshotapps.dragonshockey.extragameid";


    public static Intent createAdminAuthIntent(Context context) {
        return new Intent(context, AdminAuthActivity.class);
    }

    public static Intent createAdminIntent(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    public static Intent createAdminGameIntent(Context context, Game game) {
        Intent intent = new Intent(context, EditGameActivity.class);

        intent.putExtra(EXTRA_GAME, game);

        return intent;
    }

    public static Intent createEditGameStatsIntent(Context context, int gameID) {
        Intent intent = new Intent(context, EditStatsActivity.class);

        intent.putExtra(EXTRA_GAME_ID, gameID);

        return intent;
    }
}
