package com.slapshotapps.dragonshockey.activities.stats.adapters;


import com.slapshotapps.dragonshockey.Utils.StatsUtils;
import com.slapshotapps.dragonshockey.activities.stats.StatsListener;
import com.slapshotapps.dragonshockey.models.PlayerStats;

public class PlayerStatsVM  implements Comparable<PlayerStatsVM>, StatsListener {

    public interface PlayerStatsVMListener{
        void onViewPLayerStats(PlayerStats playerStats);
    }

    PlayerStats playerStats;
    PlayerStatsVMListener listener;

    public PlayerStatsVM(PlayerStats playerStats, PlayerStatsVMListener listener){
        this.playerStats = playerStats;
        this.listener = listener;
    }

    public String getPlayerName(){
        return StatsUtils.fullPlayerName(playerStats);
    }

    public String getLastName(){
        return playerStats.lastName;
    }

    public String getGoals(){
        return String.valueOf(playerStats.goals);
    }

    public String getAssists(){
        return String.valueOf(playerStats.assists);
    }

    public String getPoints(){
        return String.valueOf(playerStats.points);
    }

    public String getGamesPlayed(){
        return String.valueOf(playerStats.gamesPlayed);
    }

    public PlayerStats getPlayerStats(){
        return playerStats;
    }

    @Override
    public int compareTo(PlayerStatsVM playerStatsVM) {

        if (playerStats.points < Integer.valueOf(playerStatsVM.getPoints())) {
            return 1;
        } else if (playerStats.points > Integer.valueOf(playerStatsVM.getPoints())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    @Override
    public void onStatsClicked() {
        listener.onViewPLayerStats(playerStats);
    }
}
