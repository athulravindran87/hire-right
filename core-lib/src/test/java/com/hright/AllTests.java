package com.hright;

import com.hright.file.AllServiceTests;
import com.hright.model.ResumeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResumeTest.class,
        AllServiceTests.class
})
public class AllTests {
}
