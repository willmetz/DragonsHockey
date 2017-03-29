package com.slapshotapps.dragonshockey.activities.historicalStats;


import com.android.annotations.NonNull;
import com.slapshotapps.dragonshockey.Utils.RosterUtils;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerHistoricalStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoricalStatsVM {

    private final Player player;
    private List<PlayerHistoricalStats> playerHistoricalStats;

    public HistoricalStatsVM(@NonNull Player player, GameStats currentSeasonStats, List<PlayerHistoricalStats> historicalStats) {
        this.player = player;
        this.playerHistoricalStats = new ArrayList<>();
    }

    public String getPlayerName() {
        return RosterUtils.getFullName(player);
    }

    public String getPlayerNumber() {
        return String.format(Locale.US, "# %s", RosterUtils.getNumber(player));
    }

    public String getPlayerPosition() {
        return RosterUtils.getPosition(player);
    }

    public List<PlayerHistoricalStats> getStats() {
        return playerHistoricalStats;
    }

    private void filterStatsForPlayer(PlayerHistoricalStats unfilteredStats) {
        for(GameStats gameStats : unfilteredStats.games){
            
        }
    }

    private void addCurrentSeason(GameStats currentSeasonStats) {
        PlayerHistoricalStats currentSeasonFilteredStats = new PlayerHistoricalStats();
        currentSeasonFilteredStats.seasonID = "Current";
        currentSeasonFilteredStats.games = new ArrayList<>(1);
        GameStats filteredForPlayer = new GameStats();
        filteredForPlayer.gameStats = new ArrayList<>();

        for (GameStats.Stats stats : currentSeasonStats.gameStats) {
            if (stats.playerID == player.playerID) {
                filteredForPlayer.gameStats.add(stats);
            }
        }

        playerHistoricalStats.add(currentSeasonFilteredStats);
    }

}
