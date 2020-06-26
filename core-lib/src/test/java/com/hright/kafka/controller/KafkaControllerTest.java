package com.hright.kafka.controller;

import com.hright.kafka.KafkaProducer;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class KafkaControllerTest extends BaseTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private KafkaController testObj;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.testObj).build();
    }

    @Test
    public void sendMessage() {
        this.testObj.sendMessage("testTopic", "testKey", "testMessage");
        verify(this.kafkaProducer).sendMessage(eq("testTopic"), eq("testKey"), eq("testMessage"));
    }

    @Test
    public void sendMessageMvc() throws Exception {

        this.mockMvc.perform(post("/kafka/send?topic=testTopic&key=testKey")
                .content("testMessage"))
                .andExpect(content().string("Message Sent"))
                .andExpect(status().isOk());

        verify(this.kafkaProducer).sendMessage(eq("testTopic"), eq("testKey"), eq("testMessage"));
    }
}