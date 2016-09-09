package com.slapshotapps.dragonshockey.activities.stats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.PlayerStats;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Recyclerview adapter for player stats
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.PlayerStatsViewHolder>{

    private ArrayList<PlayerStats> playerStats;

    public StatsAdapter(ArrayList<PlayerStats> playerStats){
        this.playerStats = playerStats;
    }

    @Override
    public PlayerStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlayerStatsViewHolder holder, int position) {

        holder.setStats(playerStats.get(position));
    }

    @Override
    public int getItemCount() {
        return playerStats != null?playerStats.size():0;
    }

    protected static class PlayerStatsViewHolder extends RecyclerView.ViewHolder{

        PlayerStats stats;
        TextView playerName, goals, assists, points, gamesPlayed;

        public PlayerStatsViewHolder(View itemView) {
            super(itemView);

            playerName = ButterKnife.findById(itemView, R.id.player_name_stats);
            goals = ButterKnife.findById(itemView, R.id.goal_total);
            assists = ButterKnife.findById(itemView, R.id.assist_total);
            points = ButterKnife.findById(itemView, R.id.points_total);
            gamesPlayed = ButterKnife.findById(itemView, R.id.games_played);
        }

        public void setStats(PlayerStats playerStats){
            stats = playerStats;

            playerName.setText(stats.lastName);
            goals.setText(stats.goals);
            assists.setText(stats.assists);
            points.setText(stats.points);
            gamesPlayed.setText(stats.gamesPlayed);
        }
    }

}
