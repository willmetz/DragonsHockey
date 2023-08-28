package com.slapshotapps.dragonshockey.activities.stats.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.models.PlayerStats
import java.util.*

/**
 * Recyclerview adapter for player stats
 */
class StatsAdapter(playerStats: List<PlayerStats>?,
                   listener: PlayerStatsVM.PlayerStatsVMListener) :
        RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    private var playerStats: ArrayList<PlayerStatsVM>? = null

    init {
        if (playerStats != null) {
            this.playerStats = ArrayList()

            for (stats in playerStats) {
                this.playerStats!!.add(PlayerStatsVM(stats, listener))
            }

            this.playerStats!!.sort()
        }
    }

    fun updateSortOrder(sortSelection: StatSortSelection) {
        for (playerStatsVM in playerStats!!) {
            playerStatsVM.setSortSelection(sortSelection)
        }

        this.playerStats!!.sort()

        notifyDataSetChanged()
    }

//    override fun getObjForPosition(position: Int): Any {
//        return playerStats!![position]
//    }
//
//    override fun getLayoutIdForPosition(position: Int): Int {
//        return if (playerStats!![position].position == PlayerPosition.GOALIE)
//            R.layout.view_stats_goalie_card
//        else
//            R.layout.view_stats_player_card
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return playerStats!!.size
    }

    class StatsViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}
