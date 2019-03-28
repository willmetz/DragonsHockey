package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep

@Keep
class SeasonStats() {

    var stats: ArrayList<GameStats>

    var seasonID: String

    init {
        stats = ArrayList()
        seasonID = ""
    }

    constructor(seasonID: String) : this() {
        this.seasonID = seasonID
    }
}
