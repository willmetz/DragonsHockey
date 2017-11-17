package com.slapshotapps.dragonshockey.activities.stats.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.slapshotapps.dragonshockey.BR;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Recyclerview adapter for player stats
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.PlayerStatsViewHolder> {

    private ArrayList<PlayerStatsVM> playerStats;

    public StatsAdapter(List<PlayerStats> playerStats, PlayerStatsVM.PlayerStatsVMListener listener) {
        if (playerStats != null) {
            this.playerStats = new ArrayList<>();

            for (PlayerStats stats : playerStats) {
                this.playerStats.add(new PlayerStatsVM(stats, listener));
            }

            Collections.sort(this.playerStats);
        }
    }

    public void updateSortOrder(StatSortSelection sortSelection) {
        for (PlayerStatsVM playerStatsVM : playerStats) {
            playerStatsVM.setSortSelection(sortSelection);
        }

        Collections.sort(this.playerStats);

        notifyDataSetChanged();
    }

    @Override
    public PlayerStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_stats_player_card, parent, false);

        return new PlayerStatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerStatsViewHolder holder, int position) {

        holder.getBinding().setVariable(BR.data, playerStats.get(position));
        holder.getBinding().setVariable(BR.listener, playerStats.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return playerStats != null ? playerStats.size() : 0;
    }

    protected static class PlayerStatsViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public PlayerStatsViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
