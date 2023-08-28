package com.slapshotapps.dragonshockey.activities.admin.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminStatsViewModel;
import com.slapshotapps.dragonshockey.models.PlayerPosition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 11/1/16.
 */

public class AdminEditsStatsAdapter extends RecyclerView.Adapter<AdminEditsStatsAdapter.AdminViewHolder> {

    private ArrayList<AdminStatsViewModel> stats;

    public AdminEditsStatsAdapter(ArrayList<AdminStatsViewModel> stats) {

        this.stats = stats;
    }

    public ArrayList<AdminStatsViewModel> getStats() {
        return stats;
    }

    public boolean statsChanged() {
        for (AdminStatsViewModel adminStatsViewModel : stats) {
            if (adminStatsViewModel.isDirty()) {
                return true;
            }
        }

        return false;
    }

//    @Override
//    protected Object getObjForPosition(int position) {
//        return stats.get(position);
//    }
//
//    @Override
//    protected int getLayoutIdForPosition(int position) {
//        return isGoalie(position) ? R.layout.view_admin_stats_goalie_card
//                : R.layout.view_admin_stats_player_card;
//    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

    private boolean isGoalie(int position) {
        return stats.get(position).getPosition() == PlayerPosition.GOALIE;
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
