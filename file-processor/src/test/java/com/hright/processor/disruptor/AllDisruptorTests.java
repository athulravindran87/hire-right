package com.hright.processor.disruptor;

import com.hright.processor.disruptor.bean.BeanArrayTest;
import com.hright.processor.disruptor.bean.ResumeEventTest;
import com.hright.processor.disruptor.config.DisruptorConfigTest;
import com.hright.processor.disruptor.config.DisruptorPropertiesTest;
import com.hright.processor.disruptor.handlers.AllHandlerTests;
import com.hright.processor.disruptor.processor.DisruptorProcessorTest;
import com.hright.processor.disruptor.producer.EventProducerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResumeEventTest.class,
        EventProducerTest.class,
        AllHandlerTests.class,
        DisruptorProcessorTest.class,
        BeanArrayTest.class,
        DisruptorConfigTest.class,
        DisruptorPropertiesTest.class
})
public class AllDisruptorTests {
}
