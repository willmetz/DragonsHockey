package com.slapshotapps.dragonshockey.activities.admin.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.databinding.library.baseAdapters.BR;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.BaseDataBindingAdapter;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.PlayerStatsViewModel;
import java.util.ArrayList;

/**
 * Created on 11/1/16.
 */

public class AdminEditsStatsAdapter extends BaseDataBindingAdapter {

    private ArrayList<PlayerStatsViewModel> stats;

    public AdminEditsStatsAdapter(ArrayList<PlayerStatsViewModel> stats) {

        this.stats = stats;
    }

    public ArrayList<PlayerStatsViewModel> getStats() {
        return stats;
    }

    public boolean statsChanged() {
        for (PlayerStatsViewModel playerStatsViewModel : stats) {
            if (playerStatsViewModel.isDirty()) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return stats.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.view_admin_stats_player_card;
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

}
