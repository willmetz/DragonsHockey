package com.slapshotapps.dragonshockey.dialogs

enum class StatSortSelection private constructor(statName: String, index: Int) {
  GamesPlayed("Games Played", 0),
  Goals("Goals", 1),
  Assists("Assists", 2),
  Points("Points", 3),
  PenaltyMinutes("Penalty Minutes", 4);

  val statName: String
  val index: Int

  init {
    this.index = index
    this.statName = name
  }
}