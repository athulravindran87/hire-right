package com.hright.kafka;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        KafkaConsumerConfigTest.class,
        KafkaProducerConfigTest.class,
        KafkaTopicConfigTest.class
})
public class AllKafkaTests {
}
