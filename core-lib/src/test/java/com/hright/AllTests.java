package com.hright;

import com.hright.file.AllServiceTests;
import com.hright.kafka.AllKafkaTests;
import com.hright.model.ResumeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResumeTest.class,
        AllServiceTests.class,
        AllKafkaTests.class
})
public class AllTests {
}
