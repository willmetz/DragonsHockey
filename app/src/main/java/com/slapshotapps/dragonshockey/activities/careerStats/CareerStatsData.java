package com.slapshotapps.dragonshockey.activities.careerStats;

import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.SeasonStats;
import java.util.List;

public class CareerStatsData {

    public Player player;
    public List<SeasonStats> seasonStats;

    public CareerStatsData(Player player, List<SeasonStats> seasonStats) {
        this.player = player;
        this.seasonStats = seasonStats;
    }
}
