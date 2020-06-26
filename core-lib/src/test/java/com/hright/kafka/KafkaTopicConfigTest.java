package com.hright.kafka;

import com.hright.test.BaseTest;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Before;
import org.junit.Test;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

public class KafkaTopicConfigTest extends BaseTest {

    private KafkaTopicConfig testObj;

    @Before
    public void setUp() throws Exception {

        this.testObj = new KafkaTopicConfig();

        ReflectionTestUtils.setField(this.testObj, "bootstrapAddress",
                "http://server1,http://server2");

        ReflectionTestUtils.setField(this.testObj, "fileProcessorTopic",
                "FILE_PROCESSOR_TOPIC");
    }

    @Test
    public void kafkaAdmin() {
        KafkaAdmin kafkaAdmin = this.testObj.kafkaAdmin();
        assertThat(kafkaAdmin.getConfigurationProperties(), allOf(
                hasEntry(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://server1,http://server2")
        ));
    }

    @Test
    public void fileProcessorTopic() {
        NewTopic newTopic = this.testObj.fileProcessorTopic();
        assertThat(newTopic.name(), equalTo("FILE_PROCESSOR_TOPIC"));
        assertThat(newTopic.numPartitions(), equalTo(1));
        assertThat(newTopic.replicationFactor(), equalTo(Short.valueOf("1")));
    }
}