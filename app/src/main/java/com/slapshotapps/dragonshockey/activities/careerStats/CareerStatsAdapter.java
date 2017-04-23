package com.slapshotapps.dragonshockey.activities.careerStats;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.BR;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

public class CareerStatsAdapter extends RecyclerView.Adapter<CareerStatsAdapter.StatsViewHolder>
        implements StickyHeaderAdapter<CareerStatsAdapter.HeaderView> {

    final ArrayList<PlayerSeasonStatsVM> playerSeasonStats;

    public CareerStatsAdapter(){
        this.playerSeasonStats = new ArrayList<>();
    }

    public void updateStats(List<PlayerSeasonStatsVM> playerSeasonStats){
        this.playerSeasonStats.clear();
        this.playerSeasonStats.addAll(playerSeasonStats);
        notifyDataSetChanged();
    }

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);


        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {

        PlayerSeasonStatsVM seasonStats = playerSeasonStats.get(position);
        holder.getBinding().setVariable(BR.seasonData, seasonStats);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return playerSeasonStats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_player_season_stats;
    }

    @Override
    public HeaderView onCreateHeaderViewHolder(RecyclerView parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_season_stats_header, parent, false);

        return new HeaderView(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderView viewHolder) {
        //no-op
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        public StatsViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public static class HeaderView extends RecyclerView.ViewHolder {
        public HeaderView(View itemView) {
            super(itemView);
        }

        public View getContentView() {
            return itemView;
        }

    }
}
