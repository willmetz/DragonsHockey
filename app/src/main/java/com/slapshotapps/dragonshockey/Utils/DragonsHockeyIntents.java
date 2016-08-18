package com.slapshotapps.dragonshockey.Utils;

import android.content.Context;
import android.content.Intent;

import com.slapshotapps.dragonshockey.activities.roster.RosterActivity;

/**
 * Created by willmetz on 7/28/16.
 */

public class DragonsHockeyIntents {

    public static Intent createRosterIntent(Context context){
        return new Intent(context, RosterActivity.class);
    }

}
