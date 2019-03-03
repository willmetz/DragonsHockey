package com.slapshotapps.dragonshockey.activities.careerStats

import com.slapshotapps.dragonshockey.Utils.RosterUtils
import com.slapshotapps.dragonshockey.models.Player
import com.slapshotapps.dragonshockey.models.PlayerStats
import com.slapshotapps.dragonshockey.models.SeasonStats
import java.util.*

class CareerStatsVM(private val player: Player, currentSeasonStats: PlayerStats?,
    unfilteredSeasonStats: List<SeasonStats>) {
  private val playerSeasonStats: MutableList<PlayerSeasonStatsVM>

  val playerName: String
    get() = RosterUtils.getFullName(player)

  val playerNumber: String
    get() = String.format(Locale.US, "# %s", RosterUtils.getNumber(player))

  val playerPosition: String
    get() = RosterUtils.getPosition(player)

  val stats: List<PlayerSeasonStatsVM>
    get() = playerSeasonStats

  init {
    this.playerSeasonStats = ArrayList()
    filterStatsForPlayer(unfilteredSeasonStats)
    addCurrentSeason(currentSeasonStats)
    totalCareerStats()
  }

  private fun filterStatsForPlayer(unfilteredStats: List<SeasonStats>?) {

    if (unfilteredStats == null) {
      return
    }

    for (unfilteredHistoricalStats in unfilteredStats) {

      val tempPlayerSeasonStats = PlayerSeasonStatsVM(unfilteredHistoricalStats.seasonID)

      for (stats in unfilteredHistoricalStats.stats) {

        for (gameStats in stats.gameStats) {
          if (gameStats.playerID == player.playerID) {
            tempPlayerSeasonStats.goals += gameStats.goals
            tempPlayerSeasonStats.assists += gameStats.assists
            tempPlayerSeasonStats.gamesPlayed += if (gameStats.present) 1 else 0
            tempPlayerSeasonStats.penaltyMinutes += gameStats.penaltyMinutes
            tempPlayerSeasonStats.goalsAgainst += gameStats.goalsAgainst
            tempPlayerSeasonStats.shutouts += if (gameStats.goalsAgainst == 0 && gameStats.present) 1 else 0
            break
          }
        }
      }
      playerSeasonStats.add(tempPlayerSeasonStats)
    }
  }

  private fun addCurrentSeason(currentSeasonStats: PlayerStats?) {
    if (currentSeasonStats != null) {
      playerSeasonStats.add(PlayerSeasonStatsVM(currentSeasonStats, "Current"))
    }
  }

  private fun totalCareerStats() {
    val careerStats = PlayerSeasonStatsVM("Career")

    for (stats in playerSeasonStats) {
      careerStats.assists += stats.assists
      careerStats.goals += stats.goals
      careerStats.gamesPlayed += stats.gamesPlayed
      careerStats.penaltyMinutes += stats.penaltyMinutes
      careerStats.shutouts += stats.shutouts
      careerStats.goalsAgainst += stats.goalsAgainst
    }

    playerSeasonStats.add(careerStats)
  }
}
