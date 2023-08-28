package com.slapshotapps.dragonshockey.activities.careerStats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter
import com.slapshotapps.dragonshockey.models.PlayerPosition
import java.util.*

class CareerStatsAdapter : RecyclerView.Adapter<CareerStatsAdapter.CareerStatViewHolder>(), StickyHeaderAdapter<CareerStatsAdapter.HeaderView> {

    private val playerSeasonStats: ArrayList<PlayerSeasonStatsVM>
    private var playerPosition: PlayerPosition? = null

    init {
        this.playerSeasonStats = ArrayList()
        playerPosition = PlayerPosition.FORWARD
    }

    fun updateStats(playerSeasonStats: List<PlayerSeasonStatsVM>, position: PlayerPosition) {
        this.playerSeasonStats.clear()
        this.playerSeasonStats.addAll(playerSeasonStats)
        this.playerPosition = position
        notifyDataSetChanged()
    }

//    override fun getObjForPosition(position: Int): Any {
//        return playerSeasonStats[position]
//    }
//
//    override fun getLayoutIdForPosition(position: Int): Int {
//        return if (playerPosition == PlayerPosition.GOALIE) R.layout.list_goalie_season_stats else R.layout.list_player_season_stats
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerStatViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CareerStatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return playerSeasonStats.size
    }

    override fun onCreateHeaderViewHolder(parent: RecyclerView): HeaderView {
        val layoutID = if (playerPosition == PlayerPosition.GOALIE)
            R.layout.list_season_stats_goalie_header
        else
            R.layout.list_season_stats_header

        val view = LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)

        return HeaderView(view)
    }

    override fun onBindHeaderViewHolder(viewHolder: HeaderView) {
        //no-op
    }

    class HeaderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contentView: View
            get() = itemView
    }

    class CareerStatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
