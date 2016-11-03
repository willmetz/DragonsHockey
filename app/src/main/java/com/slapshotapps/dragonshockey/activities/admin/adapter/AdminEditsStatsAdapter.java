package com.slapshotapps.dragonshockey.activities.admin.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.PlayerStatsViewModel;

import java.util.ArrayList;

/**
 * Created on 11/1/16.
 */

public class AdminEditsStatsAdapter extends RecyclerView.Adapter<AdminEditsStatsAdapter.StatsViewHolder>{

    private ArrayList<PlayerStatsViewModel> stats;

    public AdminEditsStatsAdapter(ArrayList<PlayerStatsViewModel> stats){

    }

    public ArrayList<PlayerStatsViewModel> getStats(){
        return stats;
    }

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {
        final PlayerStatsViewModel model = stats.get(position);

        holder.getBinding().setVariable(BR.data, model);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        public StatsViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding(){
            return binding;
        }
    }
}
