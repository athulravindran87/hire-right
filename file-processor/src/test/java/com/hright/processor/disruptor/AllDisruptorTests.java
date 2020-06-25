package com.hright.processor.disruptor;

import com.hright.processor.disruptor.bean.ResumeEventTest;
import com.hright.processor.disruptor.handlers.AllHandlerTests;
import com.hright.processor.disruptor.producer.EventProducerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResumeEventTest.class,
        EventProducerTest.class,
        AllHandlerTests.class
})
public class AllDisruptorTests {
}
