package com.slapshotapps.dragonshockey.utils

import com.slapshotapps.dragonshockey.models.GameResult
import org.junit.Assert
import org.junit.Test

/**
 * A testing class for anything in the [FormattingUtils] class.
 */

class FormattingUtilsTest {

    @Test
    fun testNumberSuffix_zero() {
        Assert.assertEquals("0th", FormattingUtils.getValueWithSuffix(0))
    }

    @Test
    fun testNumberSuffix_one() {
        Assert.assertEquals("1st", FormattingUtils.getValueWithSuffix(1))
    }

    @Test
    fun testNumberSuffix_two() {
        Assert.assertEquals("2nd", FormattingUtils.getValueWithSuffix(2))
    }

    @Test
    fun testNumberSuffix_three() {
        Assert.assertEquals("3rd", FormattingUtils.getValueWithSuffix(3))
    }

    @Test
    fun testNumberSuffix_four_twenty() {
        for (i in 4..20) {
            Assert.assertEquals(i.toString() + "th", FormattingUtils.getValueWithSuffix(i))
        }
    }

    @Test
    fun testNumberSuffix_twenty_one() {
        Assert.assertEquals("21st", FormattingUtils.getValueWithSuffix(21))
    }

    @Test
    fun testNumberSuffix_twenty_two() {
        Assert.assertEquals("22nd", FormattingUtils.getValueWithSuffix(22))
    }

    @Test
    fun testNumberSuffix_twenty_three() {
        Assert.assertEquals("23rd", FormattingUtils.getValueWithSuffix(23))
    }

    @Test
    fun testNumberSuffix_twenty_four_thrity() {
        for (i in 24..30) {
            Assert.assertEquals(i.toString() + "th", FormattingUtils.getValueWithSuffix(i))
        }
    }

    @Test
    fun testNumberSuffix_thirty_one() {
        Assert.assertEquals("31st", FormattingUtils.getValueWithSuffix(31))
    }

    @Test
    fun testGameResult_Win() {
        val gameResult = GameResult()
        gameResult.dragonsScore = 5
        gameResult.opponentScore = 4
        gameResult.overtimeLoss = false
        Assert.assertEquals(FormattingUtils.WIN, FormattingUtils.getGameResultAsString(gameResult))
    }

    @Test
    fun testGameResult_OTL() {
        val gameResult = GameResult()
        gameResult.dragonsScore = 5
        gameResult.opponentScore = 4
        gameResult.overtimeLoss = true
        Assert.assertEquals(FormattingUtils.OVERTIME_LOSS,
                FormattingUtils.getGameResultAsString(gameResult))
    }

    @Test
    fun testGameResult_Loss() {
        val gameResult = GameResult()
        gameResult.dragonsScore = 4
        gameResult.opponentScore = 5
        gameResult.overtimeLoss = false
        Assert.assertEquals(FormattingUtils.LOSS,
                FormattingUtils.getGameResultAsString(gameResult))
    }

    @Test
    fun testGameResult_Tie() {
        val gameResult = GameResult()
        gameResult.dragonsScore = 4
        gameResult.opponentScore = 4
        gameResult.overtimeLoss = false
        Assert.assertEquals(FormattingUtils.TIE, FormattingUtils.getGameResultAsString(gameResult))
    }
}
