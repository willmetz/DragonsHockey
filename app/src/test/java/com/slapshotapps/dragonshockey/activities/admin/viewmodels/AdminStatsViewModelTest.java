package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.models.PlayerPosition;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AdminStatsViewModelTest {

    @Test
    public void testPlayerNameWhenNull(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator(null, PlayerPosition.FORWARD);

        assertThat(viewModel.getPlayerName(), is(""));
    }

    @Test
    public void testPlayerNameNonNull(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD);

        assertThat(viewModel.getPlayerName(), is("Test"));
    }

    @Test
    public void testDataIsNotDirtyWithNoChange(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD);

        assertFalse(viewModel.isDirty());
    }

    @Test
    public void testDataIsDirtyWhenGoalsUpdated(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD);

        assertFalse(viewModel.isDirty());

        viewModel.setGoals("");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getGoals(), is("0"));

        viewModel.setGoals("5");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getGoals(), is("5"));
    }

    @Test
    public void testDataIsDirtyWhenAssistsUpdated(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD);

        assertFalse(viewModel.isDirty());

        viewModel.setAssists("");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getGoals(), is("0"));

        viewModel.setAssists("3");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getAssists(), is("3"));
    }

    @Test
    public void testDataIsDirtyWhenPIMUpdated(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.FORWARD);

        assertFalse(viewModel.isDirty());

        viewModel.setPenaltyMinutes("");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getPenaltyMinutes(), is("0"));

        viewModel.setPenaltyMinutes("15");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getPenaltyMinutes(), is("15"));
    }

    @Test
    public void testDataIsDirtyWhenGoalsAgainstUpdated(){
        AdminStatsViewModel viewModel = adminStatsViewModelCreator("Test", PlayerPosition.GOALIE);

        assertFalse(viewModel.isDirty());

        viewModel.setGoalsAgainst("");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getGoalsAgainst(), is("0"));

        viewModel.setGoalsAgainst("5");
        assertTrue(viewModel.isDirty());
        assertThat(viewModel.getGoalsAgainst(), is("5"));
    }

    private AdminStatsViewModel adminStatsViewModelCreator(String name, PlayerPosition position){
        return new AdminStatsViewModel(name, 0,0,1,56,
            true, 0, position, 1);
    }
}