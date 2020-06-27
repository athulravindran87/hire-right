package com.hright.kafka.producer;

import com.hright.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KafkaProducerTest extends BaseTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer testObj;

    @Test
    public void sendMessage() {
        this.testObj.sendMessage("testTopic", "testKey", "testMessage");
        verify(this.kafkaTemplate).send(eq("testTopic"), eq("testKey"), eq("testMessage"));
    }
}