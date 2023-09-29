package com.slapshotapps.dragonshockey.activities.careerStats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter
import com.slapshotapps.dragonshockey.databinding.ListGoalieSeasonStatsBinding
import com.slapshotapps.dragonshockey.databinding.ListPlayerSeasonStatsBinding
import com.slapshotapps.dragonshockey.databinding.ListSeasonStatsGoalieHeaderBinding
import com.slapshotapps.dragonshockey.databinding.ListSeasonStatsHeaderBinding
import com.slapshotapps.dragonshockey.models.PlayerPosition

class CareerStatsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        StickyHeaderAdapter<ViewBinding> {

    private val playerSeasonStats: ArrayList<PlayerSeasonStatsVM> = ArrayList()
    private var playerPosition: PlayerPosition? = null

    init {
        playerPosition = PlayerPosition.FORWARD
    }

    fun updateStats(playerSeasonStats: List<PlayerSeasonStatsVM>, position: PlayerPosition) {
        this.playerSeasonStats.clear()
        this.playerSeasonStats.addAll(playerSeasonStats)
        this.playerPosition = position
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (playerPosition == PlayerPosition.GOALIE)
            R.layout.list_goalie_season_stats
        else R.layout.list_player_season_stats
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_goalie_season_stats -> {
                val binding = ListGoalieSeasonStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GoalieViewHolder(binding)
            }
            else -> {
                val binding = ListPlayerSeasonStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlayerStatViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = playerSeasonStats[position]
        if(holder is GoalieViewHolder){
            holder
        }
    }

    override fun getItemCount(): Int {
        return playerSeasonStats.size
    }

    override fun onCreateViewBinding(parent: RecyclerView): ViewBinding {
        return if(playerPosition == PlayerPosition.GOALIE){
            ListSeasonStatsGoalieHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }else{
            ListSeasonStatsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    }

    override fun onBindHeaderViewHolder(binding: ViewBinding) {
        //no-op
    }

    internal inner class PlayerStatViewHolder(private val binding: ListPlayerSeasonStatsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlayerSeasonStatsVM){
            binding.assists.text = item.getAssistsAsString()
            binding.gamesPlayed.text = item.getGamesPlayedAsString()
            binding.goals.text = item.getGoalsAsString()
            binding.penaltyMinutes.text = item.getPenaltyMinutesAsString()
            binding.points.text = item.points
            binding.season.text = item.seasonID
        }
    }

    internal inner class GoalieViewHolder(private val binding: ListGoalieSeasonStatsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PlayerSeasonStatsVM){
            binding.penaltyMinutes.text = item.getPenaltyMinutesAsString()
            binding.gamesPlayed.text = item.getGamesPlayedAsString()
            binding.goalsAgainst.text = item.getGoalsAgainstAsString()
            binding.goalsAgainstAverage.text = item.getGoalsAgainstAsString()
            binding.season.text = item.seasonID
        }
    }


}
