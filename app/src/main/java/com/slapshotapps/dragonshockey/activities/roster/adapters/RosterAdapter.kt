package com.slapshotapps.dragonshockey.activities.roster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.RosterUtils
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter
import com.slapshotapps.dragonshockey.databinding.ViewHeaderRosterBinding
import com.slapshotapps.dragonshockey.databinding.ViewRosterRowBinding
import com.slapshotapps.dragonshockey.models.Player
import java.util.*

/**
 * Created by willmetz on 7/31/16.
 */

class RosterAdapter(private val context: Context, roster: List<Player>) : RecyclerView.Adapter<RosterAdapter.PlayerLineView>(),
        StickyHeaderAdapter<ViewBinding> {

    private val rosterListItems: ArrayList<RosterListItem> = ArrayList()

    init {

        val sortedRoster = RosterUtils.sortRoster(roster)

        for (player in sortedRoster) {
            rosterListItems.add(RosterListItem(player))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerLineView {

        val binding = ViewRosterRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return PlayerLineView(binding)
    }

    override fun onBindViewHolder(holder: RosterAdapter.PlayerLineView, position: Int) {

        if (getItemViewType(position) == RosterListItem.ROSTER_TYPE) {
            val player = rosterListItems[position].player
            holder.setBackgroundColor(getBackgroundColor(player, position))
            holder.setPlayerInfo(player)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return RosterListItem.ROSTER_TYPE
    }

    override fun getItemCount(): Int {
        return rosterListItems.size
    }

    private fun getBackgroundColor(player: Player, position: Int): Int {
        return if (player.injuredReserved) {
            ContextCompat.getColor(context, R.color.lightRed)
        } else if (position % 2 == 0) {
            ContextCompat.getColor(context, R.color.lightGray)
        } else {
            ContextCompat.getColor(context, android.R.color.white)
        }
    }

    class PlayerLineView(private val binding: ViewRosterRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setPlayerInfo(player: Player) {
            binding.playerNumber.text = RosterUtils.getNumber(player)
            binding.playerName.text = RosterUtils.getFullName(player)
            binding.playerPosition.text = RosterUtils.getPosition(player)
        }

        fun setBackgroundColor(color: Int) {
            itemView.setBackgroundColor(color)
        }
    }

    override fun onCreateViewBinding(parent: RecyclerView): ViewBinding {
        return ViewHeaderRosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindHeaderViewHolder(binding: ViewBinding) {
        //no-op
    }
}
