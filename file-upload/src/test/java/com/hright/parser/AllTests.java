package com.hright.parser;

import com.hright.parser.controller.AllControllerTests;
import com.hright.parser.service.AllServiceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllControllerTests.class,
        AllServiceTests.class,
        ApplicationTests.class
})

public class AllTests {
}
