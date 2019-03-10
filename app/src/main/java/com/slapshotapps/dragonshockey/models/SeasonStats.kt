package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep
import java.util.*

@Keep
class SeasonStats() {

    var stats: List<GameStats>

    var seasonID: String

    init {
        stats = ArrayList()
        seasonID = ""
    }

    constructor(seasonID: String) : this() {
        this.seasonID = seasonID
    }
}
