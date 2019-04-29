package com.slapshotapps.dragonshockey.activities.careerStats

import com.slapshotapps.dragonshockey.models.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import java.util.*

class CareerStatsVMTest {

    private val teamStatsForGame: List<GameStats.Stats>
        get() {

            val stats = ArrayList<GameStats.Stats>()

            var goals = 1
            var assists = 2
            var goalsAgainst = 0
            for (playerID in 0..9) {
                stats.add(GameStats.Stats(playerID, assists++, goals++, 2, true, goalsAgainst++))
            }

            return stats
        }

    @Test
    fun testPlayerName() {

        val player = Player()
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 9

        val careerStatsVM = CareerStatsVM(player, null, null)

        assertThat(careerStatsVM.playerName, `is`("Bob Builder"))
    }

    @Test
    fun testPlayerNameNull() {

        val player = Player()
        player.playerID = 9

        val careerStatsVM = CareerStatsVM(player, null, null)

        assertThat(careerStatsVM.playerName, `is`(""))
    }

    @Test
    fun testPlayerNumber() {
        val player = Player()
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 9
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, null, null)

        assertThat(careerStatsVM.playerNumber, `is`("# 99"))
    }

    @Test
    fun testPlayerNumberEmpty() {
        val player = Player()
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 9

        val careerStatsVM = CareerStatsVM(player, null, null)

        assertThat(careerStatsVM.playerNumber, `is`("# 0"))
    }

    @Test
    fun testPlayerPosition() {
        val player = Player("F")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 9
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, null, null)

        assertThat(careerStatsVM.playerPosition, `is`("Forward"))
    }

    @Test
    fun testNonGoalieCurrentSeasonAdding() {

        val playerStats = PlayerStats(2, "bob", "Builder", PlayerPosition.FORWARD)
        playerStats.goals = 2
        playerStats.assists = 4
        playerStats.penaltyMinutes = 5
        playerStats.gamesPlayed = 10

        val player = Player("F")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 2
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, playerStats, null)

        val playerSeasonStats = careerStatsVM.stats

        assertThat(playerSeasonStats.size, `is`(2))
        assertThat(playerSeasonStats[0].goals, `is`(2))
        assertThat(playerSeasonStats[0].assists, `is`(4))
        assertThat(playerSeasonStats[0].penaltyMinutes, `is`(5))
        assertThat(playerSeasonStats[0].points, `is`(6.toString()))

        //career
        assertThat(playerSeasonStats[1].goals, `is`(2))
        assertThat(playerSeasonStats[1].assists, `is`(4))
        assertThat(playerSeasonStats[1].penaltyMinutes, `is`(5))
        assertThat(playerSeasonStats[1].points, `is`(6.toString()))
        assertThat(playerSeasonStats[1].seasonID, `is`("Career"))
    }

    @Test
    fun testGoalieCurrentSeasonAdding() {

        val playerStats = PlayerStats(2, "bob", "Builder", PlayerPosition.GOALIE)
        playerStats.penaltyMinutes = 5
        playerStats.gamesPlayed = 10
        playerStats.goalsAgainst = 18

        val player = Player("G")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 2
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, playerStats, null)

        val playerSeasonStats = careerStatsVM.stats

        assertThat(playerSeasonStats.size, `is`(2))
        assertThat(playerSeasonStats[0].penaltyMinutes, `is`(5))
        assertThat(playerSeasonStats[0].goalsAgainst, `is`(18))
        assertThat(playerSeasonStats[0].goalsAgainstAverage, `is`("1.80"))
        assertThat(playerSeasonStats[0].gamesPlayed, `is`(10))

        //career
        assertThat(playerSeasonStats[1].gamesPlayed, `is`(10))
        assertThat(playerSeasonStats[1].goalsAgainst, `is`(18))
        assertThat(playerSeasonStats[1].goalsAgainstAverage, `is`("1.80"))
        assertThat(playerSeasonStats[1].penaltyMinutes, `is`(5))
        assertThat(playerSeasonStats[1].seasonID, `is`("Career"))
    }

    @Test
    fun testNonGoalieHistoricalSeasonFiltering() {

        val seasonStats = ArrayList<SeasonStats>()
        seasonStats.add(SeasonStats())
        seasonStats.add(SeasonStats())

        for (i in 0..4) {
            seasonStats[0].stats.add(GameStats())
            seasonStats[0].stats[i].gameStats = teamStatsForGame

            seasonStats[1].stats.add(GameStats())
            seasonStats[1].stats[i].gameStats = teamStatsForGame
        }

        val player = Player("F")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 2
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, null, seasonStats)

        val playerSeasonStats = careerStatsVM.stats

        assertThat(playerSeasonStats.size, `is`(3))
        assertThat(playerSeasonStats[0].goals, `is`(15))
        assertThat(playerSeasonStats[0].assists, `is`(20))
        assertThat(playerSeasonStats[0].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[0].points, `is`(35.toString()))

        assertThat(playerSeasonStats[1].goals, `is`(15))
        assertThat(playerSeasonStats[1].assists, `is`(20))
        assertThat(playerSeasonStats[1].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[1].points, `is`(35.toString()))

        assertThat(playerSeasonStats[2].goals, `is`(30))
        assertThat(playerSeasonStats[2].assists, `is`(40))
        assertThat(playerSeasonStats[2].penaltyMinutes, `is`(20))
        assertThat(playerSeasonStats[2].points, `is`(70.toString()))
    }

    @Test
    fun testGoalieHistoricalSeasonFiltering() {

        val seasonStats = ArrayList<SeasonStats>()
        seasonStats.add(SeasonStats())
        seasonStats.add(SeasonStats())

        for (i in 0..4) {
            seasonStats[0].stats.add(GameStats())
            seasonStats[0].stats[i].gameStats = teamStatsForGame

            seasonStats[1].stats.add(GameStats())
            seasonStats[1].stats[i].gameStats = teamStatsForGame
        }

        val player = Player("G")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 4
        player.number = 99

        val careerStatsVM = CareerStatsVM(player, null, seasonStats)

        val playerSeasonStats = careerStatsVM.stats

        assertThat(playerSeasonStats.size, `is`(3))
        assertThat(playerSeasonStats[0].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[0].goalsAgainst, `is`(20))
        assertThat(playerSeasonStats[0].goalsAgainstAverage, `is`("4.00"))
        assertThat(playerSeasonStats[0].gamesPlayed, `is`(5))



        assertThat(playerSeasonStats[1].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[1].goalsAgainst, `is`(20))
        assertThat(playerSeasonStats[1].goalsAgainstAverage, `is`("4.00"))
        assertThat(playerSeasonStats[1].gamesPlayed, `is`(5))

        assertThat(playerSeasonStats[2].penaltyMinutes, `is`(20))
        assertThat(playerSeasonStats[2].goalsAgainst, `is`(40))
        assertThat(playerSeasonStats[2].goalsAgainstAverage, `is`("4.00"))
        assertThat(playerSeasonStats[2].gamesPlayed, `is`(10))

    }

    @Test
    fun testFullCareer() {
        val seasonStats = ArrayList<SeasonStats>()
        seasonStats.add(SeasonStats("Winter 16"))
        seasonStats.add(SeasonStats("Fall 17"))

        for (i in 0..4) {
            seasonStats[0].stats.add(GameStats())
            seasonStats[0].stats[i].gameStats = teamStatsForGame

            seasonStats[1].stats.add(GameStats())
            seasonStats[1].stats[i].gameStats = teamStatsForGame
        }

        val player = Player("F")
        player.firstName = "Bob"
        player.lastName = "Builder"
        player.playerID = 2
        player.number = 99

        val playerStats = PlayerStats(2, "bob", "Builder", PlayerPosition.FORWARD)
        playerStats.goals = 2
        playerStats.assists = 4
        playerStats.gamesPlayed = 10
        playerStats.penaltyMinutes = 8

        val careerStatsVM = CareerStatsVM(player, playerStats, seasonStats)

        val playerSeasonStats = careerStatsVM.stats

        assertThat(playerSeasonStats.size, `is`(4))

        assertThat(playerSeasonStats[0].goals, `is`(15))
        assertThat(playerSeasonStats[0].assists, `is`(20))
        assertThat(playerSeasonStats[0].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[0].points, `is`(35.toString()))
        assertThat(playerSeasonStats[0].seasonID, `is`("Winter 16"))

        assertThat(playerSeasonStats[1].goals, `is`(15))
        assertThat(playerSeasonStats[1].assists, `is`(20))
        assertThat(playerSeasonStats[1].penaltyMinutes, `is`(10))
        assertThat(playerSeasonStats[1].points, `is`(35.toString()))
        assertThat(playerSeasonStats[1].seasonID, `is`("Fall 17"))

        assertThat(playerSeasonStats[2].goals, `is`(2))
        assertThat(playerSeasonStats[2].assists, `is`(4))
        assertThat(playerSeasonStats[2].penaltyMinutes, `is`(8))
        assertThat(playerSeasonStats[2].points, `is`(6.toString()))
        assertThat(playerSeasonStats[2].seasonID, `is`("Current"))

        assertThat(playerSeasonStats[3].goals, `is`(32))
        assertThat(playerSeasonStats[3].assists, `is`(44))
        assertThat(playerSeasonStats[3].penaltyMinutes, `is`(28))
        assertThat(playerSeasonStats[3].points, `is`(76.toString()))
        assertThat(playerSeasonStats[3].seasonID, `is`("Career"))
    }
}
