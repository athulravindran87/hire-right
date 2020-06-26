package com.hright.kafka.config;

import com.hright.test.BaseTest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class KafkaProducerConfigTest extends BaseTest {

    private KafkaProducerConfig testObj;

    @Before
    public void setUp() throws Exception {
        this.testObj = new KafkaProducerConfig();

        ReflectionTestUtils.setField(this.testObj, "bootStrapServers",
                "http://server1,http://server2");
    }

    @Test
    public void producerFactory() {
        ProducerFactory<String, String> producerFactory = this.testObj.producerFactory();
        Map<String, Object> configurationProperties = producerFactory.getConfigurationProperties();

        assertThat(configurationProperties, allOf(
                hasEntry(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://server1,http://server2"),
                hasEntry(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"),
                hasEntry(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        ));
    }

    @Ignore
    @Test
    public void kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = this.testObj.kafkaTemplate();
        assertNotNull(kafkaTemplate);

        assertThat(kafkaTemplate.getProducerFactory().getConfigurationProperties(), allOf(
                hasEntry(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://server1,http://server2"),
                hasEntry(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"),
                hasEntry(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        ));
    }
}