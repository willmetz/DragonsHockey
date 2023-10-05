package com.slapshotapps.dragonshockey.activities.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.activities.admin.adapter.AdminScheduleAdapter.ScheduleViewHolder
import com.slapshotapps.dragonshockey.activities.admin.listeners.AdminClickListener
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.GameListItem
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.ListItem
import com.slapshotapps.dragonshockey.databinding.ViewAdminGameCardBinding
import com.slapshotapps.dragonshockey.models.SeasonSchedule

/**
 * Created on 10/9/16.
 */
class AdminScheduleAdapter(schedule: SeasonSchedule, private val listener: AdminClickListener) : RecyclerView.Adapter<ScheduleViewHolder>() {
    private val items: ArrayList<GameListItem> = ArrayList()

    init {
        for (game in schedule.allGames) {
            items.add(GameListItem(game))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ViewAdminGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutID
    }

    class ScheduleViewHolder(private val binding: ViewAdminGameCardBinding, private val listener: AdminClickListener) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: GameListItem){
            binding.gameDate.text = data.gameDate
            binding.gameId.text = data.gameID
            binding.gameOpponent.text = data.gameOpponent
            binding.gameResult.text = data.gameResult
            binding.root.setOnClickListener {
                listener.onGameClick(data.game)
            }
        }
    }
}