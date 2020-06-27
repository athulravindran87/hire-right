package com.hright.processor.kafka;

import com.hright.test.BaseTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.verify;


public class KafkaConsumerTest extends BaseTest {

    private KafkaConsumer testObj;

    @Before
    public void setUp() throws Exception {
        this.testObj = new KafkaConsumer();
    }

    @Test
    public void consumeMessage() {
        Acknowledgment acknowledgment = Mockito.mock(Acknowledgment.class);
        ConsumerRecord<String, String> record = new ConsumerRecord("topic-1", 0, 1L, "key1", "value1");
        this.testObj.consumeMessage(record, acknowledgment);

        verify(acknowledgment).acknowledge();
    }
}