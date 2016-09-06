package com.slapshotapps.dragonshockey.activities.stats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.models.PlayerStats;

/**
 * Created by willmetz on 9/5/16.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.PlayerStatsViewHolder{


    @Override
    public PlayerStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlayerStatsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PlayerStatsViewHolder extends RecyclerView.ViewHolder{

        PlayerStats stats;

        public PlayerStatsViewHolder(View itemView) {
            super(itemView);
        }

        public void setStats(PlayerStats playerStats){
            stats = playerStats;
        }
    }

}
