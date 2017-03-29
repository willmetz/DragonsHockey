package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.android.annotations.NonNull;
import com.slapshotapps.dragonshockey.Utils.RosterUtils;
import com.slapshotapps.dragonshockey.models.Player;

import java.util.Locale;

public class HistoricalStatsVM {

    private final Player player;

    public HistoricalStatsVM(@NonNull Player player){
        this.player = player;
    }

    public String getPlayerName(){
        return RosterUtils.getFullName(player);
    }

    public String getPlayerNumber(){
        return String.format(Locale.US, "# %s", RosterUtils.getNumber(player));
    }

    public String getPlayerPosition(){
        return RosterUtils.getPosition(player);
    }

}
