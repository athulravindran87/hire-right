package com.hright;

import com.hright.kafka.AllKafkaTests;
import com.hright.local.AllLocalTests;
import com.hright.model.ResumeTest;
import com.hright.remote.AwsFileServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResumeTest.class,
        AllLocalTests.class,
        AllKafkaTests.class,
        AwsFileServiceTest.class
})
public class AllTests {
}
