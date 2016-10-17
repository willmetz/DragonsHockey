package com.slapshotapps.dragonshockey.Utils;

import android.content.Context;
import android.content.Intent;

import com.slapshotapps.dragonshockey.activities.admin.EditGameAuthActivity;
import com.slapshotapps.dragonshockey.activities.admin.AdminActivity;
import com.slapshotapps.dragonshockey.activities.admin.AdminAuthActivity;
import com.slapshotapps.dragonshockey.activities.roster.RosterActivity;
import com.slapshotapps.dragonshockey.activities.schedule.ScheduleActivity;
import com.slapshotapps.dragonshockey.activities.stats.StatsActivity;
import com.slapshotapps.dragonshockey.models.Game;


public class DragonsHockeyIntents {

    public static final String EXTRA_GAME = "com.slapshotapps.dragonshockey.extragame";

    public static Intent createRosterIntent(Context context){
        return new Intent(context, RosterActivity.class);
    }

    public static Intent createStatsIntent(Context context){
        return new Intent(context, StatsActivity.class);
    }

    public static Intent createScheduleIntent(Context context){
        return new Intent(context, ScheduleActivity.class);
    }

    public static Intent createAdminAuthIntent(Context context, boolean isLoggedIn){

        if(isLoggedIn){
            return createAdminIntent(context);
        }else {
            return new Intent(context, AdminAuthActivity.class);
        }
    }

    public static Intent createAdminIntent(Context context){
        return new Intent(context, AdminActivity.class);
    }

    public static Intent createAdminGameIntent(Context context, Game game){
        Intent intent = new Intent(context, EditGameAuthActivity.class);

        intent.putExtra(EXTRA_GAME, game);

        return intent;
    }

}
