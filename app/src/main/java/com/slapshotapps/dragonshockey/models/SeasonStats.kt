package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep

@Keep
class SeasonStats() {

    @Keep
    @JvmField
    var stats = ArrayList<GameStats>()

    var seasonID = ""

    constructor(seasonID: String) : this() {
        this.seasonID = seasonID
    }
}
