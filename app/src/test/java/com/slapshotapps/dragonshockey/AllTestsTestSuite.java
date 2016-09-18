package com.slapshotapps.dragonshockey;

import com.slapshotapps.dragonshockey.Utils.UtilsTestSuite;
import com.slapshotapps.dragonshockey.models.ModelsTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by willmetz on 9/17/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ModelsTestSuite.class,
        UtilsTestSuite.class
})
public class AllTestsTestSuite {
}
