package com.slapshotapps.dragonshockey;

import com.slapshotapps.dragonshockey.Utils.FormattingUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * A testing class for anything in the {@link FormattingUtils} class.
 */

public class FormattingUtilsTest
{

    @Test
    public void testNumberSuffix_zero()
    {
        Assert.assertEquals( "0th", FormattingUtils.getValueWithSuffix(0));
    }

    @Test
    public void testNumberSuffix_one()
    {
        Assert.assertEquals( "1st", FormattingUtils.getValueWithSuffix(1));
    }

    @Test
    public void testNumberSuffix_two()
    {
        Assert.assertEquals( "2nd", FormattingUtils.getValueWithSuffix(2));
    }

    @Test
    public void testNumberSuffix_three()
    {
        Assert.assertEquals( "3rd", FormattingUtils.getValueWithSuffix(3));
    }

    @Test
    public void testNumberSuffix_four_twenty()
    {
        for( int i = 4; i < 21; i++) {
            Assert.assertEquals(i + "th", FormattingUtils.getValueWithSuffix(i));
        }
    }

    @Test
    public void testNumberSuffix_twenty_one()
    {
        Assert.assertEquals( "21st", FormattingUtils.getValueWithSuffix(21));
    }

    @Test
    public void testNumberSuffix_twenty_two()
    {
        Assert.assertEquals( "22nd", FormattingUtils.getValueWithSuffix(22));
    }

    @Test
    public void testNumberSuffix_twenty_three()
    {
        Assert.assertEquals( "23rd", FormattingUtils.getValueWithSuffix(23));
    }

    @Test
    public void testNumberSuffix_twenty_four_thrity()
    {
        for( int i = 24; i < 31; i++) {
            Assert.assertEquals(i + "th", FormattingUtils.getValueWithSuffix(i));
        }
    }

    @Test
    public void testNumberSuffix_thirty_one()
    {
        Assert.assertEquals( "31st", FormattingUtils.getValueWithSuffix(31));
    }
}
