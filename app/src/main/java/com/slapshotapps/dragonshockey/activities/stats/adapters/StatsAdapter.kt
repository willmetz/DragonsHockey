package com.slapshotapps.dragonshockey.activities.stats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.slapshotapps.dragonshockey.databinding.ViewStatsGoalieCardBinding
import com.slapshotapps.dragonshockey.databinding.ViewStatsPlayerCardBinding
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.models.PlayerPosition
import com.slapshotapps.dragonshockey.models.PlayerStats

/**
 * Recyclerview adapter for player stats
 */
class StatsAdapter(stats: List<PlayerStats>,
                   listener: PlayerStatsVM.PlayerStatsVMListener) :
        RecyclerView.Adapter<ViewHolder>() {

    private var playerStats = arrayListOf<PlayerStatsVM>()

    init {
        stats.forEach {
            this.playerStats.add(PlayerStatsVM(it, listener))
        }
        this.playerStats.sort()
    }

    fun updateSortOrder(sortSelection: StatSortSelection) {
        for (playerStatsVM in playerStats) {
            playerStatsVM.setSortSelection(sortSelection)
        }
        playerStats.sort()

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            GOALIE_VIEW_TYPE -> {
                val binding = ViewStatsGoalieCardBinding.inflate(inflater, parent, false)
                GoalieStatsViewHolder(binding)
            }
            else -> {
                val binding = ViewStatsPlayerCardBinding.inflate(inflater, parent, false)
                PlayerStatsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is GoalieStatsViewHolder){
            holder.onBind(playerStats[position])
        }else{
            (holder as? PlayerStatsViewHolder)?.onBind(playerStats[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(playerStats.getOrNull(position)?.position == PlayerPosition.GOALIE){
            GOALIE_VIEW_TYPE
        }else{
            PLAYER_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return playerStats.size
    }

    inner class PlayerStatsViewHolder(private val binding: ViewStatsPlayerCardBinding ) : ViewHolder(binding.root) {

        fun onBind(model: PlayerStatsVM){
            binding.playerName.text = model.playerName
            binding.playerPosition.text = model.position()

            binding.gamesPlayed.text = model.gamesPlayed
            binding.goals.text = model.goals
            binding.assists.text = model.assists
            binding.points.text = model.points
            binding.penaltyMinutes.text = model.penaltyMinutes
        }
    }

    inner class GoalieStatsViewHolder(private val binding: ViewStatsGoalieCardBinding) : ViewHolder(binding.root){

        fun onBind(model: PlayerStatsVM){
            binding.playerName.text = model.playerName
            binding.playerPosition.text = model.position()


            binding.gamesPlayed.text = model.gamesPlayed
            binding.goalsAgainst.text = model.goalsAgainst()
            binding.goalsAgainstAverage.text = model.goalsAgainstAverage()
            binding.shutouts.text = model.shutouts()
            binding.penaltyMinutes.text = model.penaltyMinutes
        }
    }

    companion object{
        const val GOALIE_VIEW_TYPE = 1
        const val PLAYER_VIEW_TYPE = 2
    }
}
