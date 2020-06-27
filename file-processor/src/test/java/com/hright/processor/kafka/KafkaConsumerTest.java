package com.hright.processor.kafka;

import com.hright.model.Resume;
import com.hright.processor.parser.service.ProcessorService;
import com.hright.test.BaseTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.support.Acknowledgment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KafkaConsumerTest extends BaseTest {

    @Captor
    private ArgumentCaptor<Resume> argumentCaptor;

    @Mock
    private ProcessorService processorService;

    @InjectMocks
    private KafkaConsumer testObj;

    @Test
    public void consumeMessage() {
        Acknowledgment acknowledgment = Mockito.mock(Acknowledgment.class);
        ConsumerRecord<String, String> record = new ConsumerRecord("topic-1", 0, 1L, "key1", "{\"id\":\"key2\",\"body\":\"somebody\",\"resumeUrl\":\"http://someurl\"}");
        this.testObj.consumeMessage(record, acknowledgment);

        verify(this.processorService).process(argumentCaptor.capture());
        Resume captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getId(), equalTo("key2"));
        assertThat(captorValue.getBody(), equalTo("somebody"));
        assertThat(captorValue.getResumeUrl(), equalTo("http://someurl"));
        verify(acknowledgment).acknowledge();
    }
}