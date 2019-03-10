package com.slapshotapps.dragonshockey.dialogs

enum class StatSortSelection constructor(val statName: String, val index: Int) {
    GamesPlayed("Games Played", 0),
    Goals("Goals", 1),
    Assists("Assists", 2),
    Points("Points", 3),
    PenaltyMinutes("Penalty Minutes", 4);
}