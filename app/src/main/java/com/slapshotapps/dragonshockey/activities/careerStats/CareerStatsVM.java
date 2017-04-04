package com.slapshotapps.dragonshockey.activities.careerStats;


import com.android.annotations.NonNull;
import com.slapshotapps.dragonshockey.Utils.RosterUtils;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerSeasonStats;
import com.slapshotapps.dragonshockey.models.SeasonStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CareerStatsVM {

    private final Player player;
    private List<PlayerSeasonStats> playerSeasonStats;

    public CareerStatsVM(@NonNull Player player, List<GameStats> currentSeasonStats, List<SeasonStats> unfilteredSeasonStats) {
        this.player = player;
        this.playerSeasonStats = new ArrayList<>();
        filterStatsForPlayer(unfilteredSeasonStats);
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

    public List<PlayerSeasonStats> getStats() {
        return playerSeasonStats;
    }

    private void filterStatsForPlayer(List<SeasonStats> unfilteredStats) {

        if(unfilteredStats == null){
            return;
        }

        for(SeasonStats unfilteredHistoricalStats : unfilteredStats){

            PlayerSeasonStats tempPlayerSeasonStats = new PlayerSeasonStats(unfilteredHistoricalStats.seasonID);

            for (GameStats stats : unfilteredHistoricalStats.stats) {

                for (GameStats.Stats gameStats: stats.gameStats ){
                    if(gameStats.playerID == player.playerID){
                        tempPlayerSeasonStats.goals += gameStats.goals;
                        tempPlayerSeasonStats.assists += gameStats.assists;
                        tempPlayerSeasonStats.gamesPlayed += gameStats.present?1:0;
                        break;
                    }
                }
            }
            playerSeasonStats.add(tempPlayerSeasonStats);
        }

    }

    private void addCurrentSeason(List<GameStats> unfilteredStats) {

        if(unfilteredStats == null){
            return;
        }

        PlayerSeasonStats tempPlayerSeasonStats = new PlayerSeasonStats("Current");

        for(GameStats gameStats : unfilteredStats){
            for(GameStats.Stats stats : gameStats.gameStats){
                if(stats.playerID == player.playerID){
                    tempPlayerSeasonStats.assists += stats.assists;
                    tempPlayerSeasonStats.goals += stats.goals;
                    tempPlayerSeasonStats.gamesPlayed += stats.present?1:0;
                }
            }
        }
        playerSeasonStats.add(tempPlayerSeasonStats);
    }

}
