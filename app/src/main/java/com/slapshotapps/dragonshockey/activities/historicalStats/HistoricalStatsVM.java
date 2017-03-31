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

    public HistoricalStatsVM(@NonNull Player player, List<GameStats> currentSeasonStats, List<PlayerHistoricalStats> unfilteredHistoricalStats) {
        this.player = player;
        this.playerHistoricalStats = new ArrayList<>();
        filterStatsForPlayer(unfilteredHistoricalStats);
        addCurrentSeason(currentSeasonStats);
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

    private void filterStatsForPlayer(List<PlayerHistoricalStats> unfilteredStats) {

        if(unfilteredStats == null){
            return;
        }

        for(PlayerHistoricalStats unfilteredHistoricalStats : unfilteredStats){

            PlayerHistoricalStats currentSeasonFilteredStats = new PlayerHistoricalStats();
            currentSeasonFilteredStats.seasonID = unfilteredHistoricalStats.seasonID;
            currentSeasonFilteredStats.gamesStats = new ArrayList<>();

            for (GameStats.Stats gameStats : unfilteredHistoricalStats.gamesStats) {

                if(gameStats.playerID == player.playerID){
                    currentSeasonFilteredStats.gamesStats.add(gameStats);
                }

            }

            playerHistoricalStats.add(currentSeasonFilteredStats);

        }

    }

    private void addCurrentSeason(List<GameStats> unfilteredStats) {

        if(unfilteredStats == null){
            return;
        }

        PlayerHistoricalStats currentSeasonFilteredStats = new PlayerHistoricalStats();
        currentSeasonFilteredStats.seasonID = "Current";
        currentSeasonFilteredStats.gamesStats = new ArrayList<>();

        for(GameStats gameStats : unfilteredStats){
            currentSeasonFilteredStats.gamesStats.add(gameStats.getPlayerStats(player.playerID));
        }


        playerHistoricalStats.add(currentSeasonFilteredStats);
    }

}
