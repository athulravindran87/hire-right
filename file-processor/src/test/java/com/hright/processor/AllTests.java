package com.hright.processor;

import com.hright.processor.disruptor.AllDisruptorTests;
import com.hright.processor.parser.controller.AllControllerTests;
import com.hright.processor.parser.service.AllServiceTests;
import com.hright.processor.parser.tess4j.AllParserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllControllerTests.class,
        AllParserTests.class,
        AllServiceTests.class,
        AllDisruptorTests.class,
        ApplicationTests.class
})

public class AllTests {
}
