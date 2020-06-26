package com.hright.kafka;

import com.hright.kafka.config.KafkaConsumerConfigTest;
import com.hright.kafka.config.KafkaProducerConfigTest;
import com.hright.kafka.config.KafkaTopicConfigTest;
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
