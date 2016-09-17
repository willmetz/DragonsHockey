package com.slapshotapps.dragonshockey.activities.stats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.PlayerStats;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Recyclerview adapter for player stats
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.PlayerStatsViewHolder>{

    private ArrayList<PlayerStats> playerStats;

    public StatsAdapter(List<PlayerStats> playerStats){
        this.playerStats = new ArrayList<>(playerStats);
    }

    @Override
    public PlayerStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_stats_player_card, parent, false);

        return new PlayerStatsViewHolder(view);
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

        private PlayerStats stats;
        private TextView playerName, goals, points, gamesPlayed;
        private TextView assists;

        public PlayerStatsViewHolder(View itemView) {
            super(itemView);

            playerName = (TextView)itemView.findViewById(R.id.player_name);
            goals = ButterKnife.findById(itemView, R.id.goals);
            assists = ButterKnife.findById(itemView, R.id.assists);
            points = ButterKnife.findById(itemView, R.id.points);
            gamesPlayed = ButterKnife.findById(itemView, R.id.games_played);
        }

        public void setStats(PlayerStats playerStats){
            stats = playerStats;

            playerName.setText(stats.lastName);
            goals.setText(String.valueOf(stats.goals));
            assists.setText(String.valueOf(stats.assists));
            points.setText(String.valueOf(stats.points));
            gamesPlayed.setText(String.valueOf(stats.gamesPlayed));
        }
    }

}
