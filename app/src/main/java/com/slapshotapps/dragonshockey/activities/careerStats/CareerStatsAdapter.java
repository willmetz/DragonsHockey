package com.slapshotapps.dragonshockey.activities.careerStats;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.slapshotapps.dragonshockey.BR;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.BaseDataBindingAdapter;
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter;
import com.slapshotapps.dragonshockey.models.PlayerPosition;
import java.util.ArrayList;
import java.util.List;

public class CareerStatsAdapter extends BaseDataBindingAdapter
    implements StickyHeaderAdapter<CareerStatsAdapter.HeaderView> {

    private final ArrayList<PlayerSeasonStatsVM> playerSeasonStats;
    private PlayerPosition playerPosition;

    public CareerStatsAdapter() {
        this.playerSeasonStats = new ArrayList<>();
        playerPosition = PlayerPosition.FORWARD;
    }

    public void updateStats(List<PlayerSeasonStatsVM> playerSeasonStats, PlayerPosition position) {
        this.playerSeasonStats.clear();
        this.playerSeasonStats.addAll(playerSeasonStats);
        this.playerPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected Object getObjForPosition(int position) {
        return playerSeasonStats.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return playerPosition == PlayerPosition.GOALIE? R.layout.list_goalie_season_stats: R.layout.list_player_season_stats;
    }

    @Override
    public int getItemCount() {
        return playerSeasonStats.size();
    }

    @Override
    public HeaderView onCreateHeaderViewHolder(RecyclerView parent) {
        final int layoutID = playerPosition == PlayerPosition.GOALIE? R.layout.list_season_stats_goalie_header:
            R.layout.list_season_stats_header;

        final View view = LayoutInflater.from(parent.getContext())
            .inflate(layoutID, parent, false);

        return new HeaderView(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderView viewHolder) {
        //no-op
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
