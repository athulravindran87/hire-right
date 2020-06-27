package com.hright.local;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LocalFileServiceTest.class,
        LocalMessageProducerTest.class
})
public class AllLocalTests {
}
