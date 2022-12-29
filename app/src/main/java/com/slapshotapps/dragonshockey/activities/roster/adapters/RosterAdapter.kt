package com.slapshotapps.dragonshockey.activities.roster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.RosterUtils
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter
import com.slapshotapps.dragonshockey.models.Player
import java.util.*

/**
 * Created by willmetz on 7/31/16.
 */

class RosterAdapter(private val context: Context, roster: List<Player>) : RecyclerView.Adapter<RosterAdapter.PlayerLineView>(), StickyHeaderAdapter<RosterAdapter.HeaderView> {

    private val rosterListItems: ArrayList<RosterListItem> = ArrayList()

    init {

        val sortedRoster = RosterUtils.sortRoster(roster)

        for (player in sortedRoster) {
            rosterListItems.add(RosterListItem(player))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterAdapter.PlayerLineView {

        val view = LayoutInflater.from(context).inflate(R.layout.view_roster_row, parent, false)
        return PlayerLineView(view)
    }

    override fun onBindViewHolder(holder: RosterAdapter.PlayerLineView, position: Int) {

        if (getItemViewType(position) == RosterListItem.ROSTER_TYPE) {
            val player = rosterListItems[position].player

            holder.setBackgroundColor(getBackgroundColor(player, position))

            holder.setPlayer(player)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return RosterListItem.ROSTER_TYPE
    }

    override fun getItemCount(): Int {
        return rosterListItems.size
    }

    override fun onCreateHeaderViewHolder(parent: RecyclerView): RosterAdapter.HeaderView {
        val view = LayoutInflater.from(context).inflate(R.layout.view_header_roster, parent, false)
        return HeaderView(view)
    }

    override fun onBindHeaderViewHolder(viewHolder: RosterAdapter.HeaderView) {
        //nothing to do here as the data doesn't change based on position
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

    class PlayerLineView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var player: Player? = null
        private val name: TextView
        private val number: TextView
        private val position: TextView

        init {

            name = itemView.findViewById<View>(R.id.player_name) as TextView
            number = itemView.findViewById<View>(R.id.player_number) as TextView
            position = itemView.findViewById<View>(R.id.player_position) as TextView
        }

        fun setPlayer(player: Player) {
            this.player = player

            number.text = RosterUtils.getNumber(player)
            name.text = RosterUtils.getFullName(player)
            position.text = RosterUtils.getPosition(player)
        }

        fun setBackgroundColor(color: Int) {
            itemView.setBackgroundColor(color)
        }
    }

    class HeaderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contentView: View
            get() = itemView
    }
}
