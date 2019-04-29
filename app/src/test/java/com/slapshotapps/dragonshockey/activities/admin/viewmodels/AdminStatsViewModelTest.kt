package com.slapshotapps.dragonshockey.activities.admin.viewmodels

import com.slapshotapps.dragonshockey.models.PlayerPosition
import org.junit.Before
import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*

class AdminStatsViewModelTest {

    @Test
    fun testPlayerNameWhenNull() {
        val viewModel = adminStatsViewModelCreator(null, PlayerPosition.FORWARD)

        assertThat(viewModel.playerName, `is`(""))
    }

    @Test
    fun testPlayerNameNonNull() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD)

        assertThat(viewModel.playerName, `is`("Test"))
    }

    @Test
    fun testDataIsNotDirtyWithNoChange() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD)

        assertFalse(viewModel.isDirty)
    }

    @Test
    fun testDataIsDirtyWhenGoalsUpdated() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD)

        assertFalse(viewModel.isDirty)

        viewModel.goals = ""
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.goals, `is`("0"))

        viewModel.goals = "5"
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.goals, `is`("5"))
    }

    @Test
    fun testDataIsDirtyWhenAssistsUpdated() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD)

        assertFalse(viewModel.isDirty)

        viewModel.assists = ""
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.goals, `is`("0"))

        viewModel.assists = "3"
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.assists, `is`("3"))
    }

    @Test
    fun testDataIsDirtyWhenPIMUpdated() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD)

        assertFalse(viewModel.isDirty)

        viewModel.penaltyMinutes = ""
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.penaltyMinutes, `is`("0"))

        viewModel.penaltyMinutes = "15"
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.penaltyMinutes, `is`("15"))
    }

    @Test
    fun testDataIsDirtyWhenGoalsAgainstUpdated() {
        val viewModel = adminStatsViewModelCreator("Test", PlayerPosition.GOALIE)

        assertFalse(viewModel.isDirty)

        viewModel.goalsAgainst = ""
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.goalsAgainst, `is`("0"))

        viewModel.goalsAgainst = "5"
        assertTrue(viewModel.isDirty)
        assertThat(viewModel.goalsAgainst, `is`("5"))
    }

    private fun adminStatsViewModelCreator(name: String?, position: PlayerPosition): AdminStatsViewModel {
        return AdminStatsViewModel(name, 0, 0, 1, 56,
                true, 0, position, 1)
    }
}