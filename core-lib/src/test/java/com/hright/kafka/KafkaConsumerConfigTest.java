package com.hright.kafka;

import com.hright.test.BaseTest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertNotNull;

public class KafkaConsumerConfigTest extends BaseTest {

    private KafkaConsumerConfig testObj;

    @Before
    public void setUp() throws Exception {

        this.testObj = new KafkaConsumerConfig();

        ReflectionTestUtils.setField(this.testObj, "bootStrapServers",
                "http://server1,http://server2");

        ReflectionTestUtils.setField(this.testObj, "enableAutoCommit",
                true);

        ReflectionTestUtils.setField(this.testObj, "autoOffSetReset",
                "earliest");
    }

    @Test
    public void consumerFactory() {
        ConsumerFactory<String, String> consumerFactory = this.testObj.consumerFactory();

        assertThat(consumerFactory.getConfigurationProperties(), allOf(
                hasEntry(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://server1,http://server2"),
                hasEntry(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        ));
    }

    @Test
    public void kafkaListenerContainerFactory() {
        assertNotNull(this.testObj.kafkaListenerContainerFactory());
    }
}