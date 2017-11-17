package com.slapshotapps.dragonshockey;

import com.slapshotapps.dragonshockey.Utils.UtilsTestSuite;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.ViewModelTestSuite;
import com.slapshotapps.dragonshockey.activities.careerStats.CareerStatsTestSuite;
import com.slapshotapps.dragonshockey.activities.stats.adapters.PlayerStatsTestSuite;
import com.slapshotapps.dragonshockey.models.ModelsTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by willmetz on 9/17/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
                        ModelsTestSuite.class, UtilsTestSuite.class, ViewModelTestSuite.class,
                        CareerStatsTestSuite.class, PlayerStatsTestSuite.class
                    })
public class AllTestsTestSuite {
}
