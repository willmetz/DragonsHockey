package com.slapshotapps.dragonshockey.activities.roster.adapters

import com.slapshotapps.dragonshockey.models.Player

/**
 * Created by willmetz on 8/3/16.
 */

class RosterListItem(var player: Player) {

    val rosterItemType: Int

    init {
        rosterItemType = ROSTER_TYPE
    }


    companion object {
        val HEADER_TYPE = 1
        val ROSTER_TYPE = 2
    }
}
