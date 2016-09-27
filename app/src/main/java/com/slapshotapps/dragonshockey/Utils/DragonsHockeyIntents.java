package com.slapshotapps.dragonshockey.Utils;

import android.content.Context;
import android.content.Intent;

import com.slapshotapps.dragonshockey.activities.admin.AdminActivity;
import com.slapshotapps.dragonshockey.activities.home.HomeActivity;
import com.slapshotapps.dragonshockey.activities.roster.RosterActivity;
import com.slapshotapps.dragonshockey.activities.schedule.ScheduleActivity;
import com.slapshotapps.dragonshockey.activities.stats.StatsActivity;

/**
 * Created by willmetz on 7/28/16.
 */

public class DragonsHockeyIntents {

    public static Intent createRosterIntent(Context context){
        return new Intent(context, RosterActivity.class);
    }

    public static Intent createStatsIntent(Context context){
        return new Intent(context, StatsActivity.class);
    }

    public static Intent createScheduleIntent(Context context){
        return new Intent(context, ScheduleActivity.class);
    }

    public static Intent createAdminIntent(Context context){
        return new Intent(context, AdminActivity.class);
    }

}
