package com.slapshotapps.dragonshockey.activities.stats.adapters;

import android.support.annotation.NonNull;
import com.slapshotapps.dragonshockey.Utils.StatsUtils;
import com.slapshotapps.dragonshockey.activities.stats.StatsListener;
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection;
import com.slapshotapps.dragonshockey.dialogs.StatsSortDialogFragment;
import com.slapshotapps.dragonshockey.models.PlayerStats;

public class PlayerStatsVM implements Comparable<PlayerStatsVM>, StatsListener {

    public interface PlayerStatsVMListener {
        void onViewPLayerStats(PlayerStats playerStats);
    }

    private final PlayerStats playerStats;
    private final PlayerStatsVMListener listener;
    private StatSortSelection sortSelection;

    public PlayerStatsVM(PlayerStats playerStats, PlayerStatsVMListener listener) {
        this.playerStats = playerStats;
        this.listener = listener;
        sortSelection = StatSortSelection.Points;
    }

    public void setSortSelection(StatSortSelection sortSelection){
        this.sortSelection = sortSelection;
    }

    public String getPlayerName() {
        return StatsUtils.fullPlayerName(playerStats);
    }

    public String getLastName() {
        return playerStats.lastName;
    }

    public String getGoals() {
        return String.valueOf(playerStats.goals);
    }

    public String getAssists() {
        return String.valueOf(playerStats.assists);
    }

    public String getPoints() {
        return String.valueOf(playerStats.points);
    }

    public String getPenaltyMinutes() {
        return String.valueOf(playerStats.penaltyMinutes);
    }

    public String getGamesPlayed() {
        return String.valueOf(playerStats.gamesPlayed);
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    @Override
    public int compareTo(@NonNull PlayerStatsVM playerStatsVM) {

        switch (sortSelection) {
            case Goals:
                return sortByGoals(playerStatsVM);
            case Assists:
                return sortByAssists(playerStatsVM);
            case GamesPlayed:
                return sortByGamesPlayed(playerStatsVM);
            case PenaltyMinutes:
                return sortByPenaltyMinutes(playerStatsVM);
            case Points:
                default:
                return sortByPoints(playerStatsVM);
        }
    }

    private int sortByPoints(PlayerStatsVM playerStatsVM) {
        if (playerStats.points < Integer.valueOf(playerStatsVM.getPoints())) {
            return 1;
        } else if (playerStats.points > Integer.valueOf(playerStatsVM.getPoints())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    private int sortByGoals(PlayerStatsVM playerStatsVM) {
        if (playerStats.goals < Integer.valueOf(playerStatsVM.getGoals())) {
            return 1;
        } else if (playerStats.goals > Integer.valueOf(playerStatsVM.getGoals())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    private int sortByAssists(PlayerStatsVM playerStatsVM) {
        if (playerStats.assists < Integer.valueOf(playerStatsVM.getAssists())) {
            return 1;
        } else if (playerStats.assists > Integer.valueOf(playerStatsVM.getAssists())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    private int sortByGamesPlayed(PlayerStatsVM playerStatsVM) {
        if (playerStats.gamesPlayed < Integer.valueOf(playerStatsVM.getGamesPlayed())) {
            return 1;
        } else if (playerStats.gamesPlayed > Integer.valueOf(playerStatsVM.getGamesPlayed())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    private int sortByPenaltyMinutes(PlayerStatsVM playerStatsVM) {
        if (playerStats.penaltyMinutes < Integer.valueOf(playerStatsVM.getPenaltyMinutes())) {
            return 1;
        } else if (playerStats.penaltyMinutes > Integer.valueOf(
            playerStatsVM.getPenaltyMinutes())) {
            return -1;
        }
        return playerStats.lastName.compareTo(playerStatsVM.getLastName());
    }

    @Override
    public void onStatsClicked() {
        listener.onViewPLayerStats(playerStats);
    }
}
