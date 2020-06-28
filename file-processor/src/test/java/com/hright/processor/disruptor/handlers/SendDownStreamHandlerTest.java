package com.hright.processor.disruptor.handlers;

import com.hright.MessageProducer;
import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SendDownStreamHandlerTest extends BaseTest {

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private SendDownStreamHandler testObj;


    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(this.testObj, "elasticSearchTopic", "topic2");
    }

    @Test
    public void onEvent() throws Exception {
        ResumeEvent event = this.createEvent();
        this.testObj.onEvent(event);

        verify(this.messageProducer).sendMessage(eq("topic2"), anyString(), anyString());
    }

    private ResumeEvent createEvent() {
        ResumeEvent resumeEvent = new ResumeEvent();
        resumeEvent.setSequenceId(123L);
        resumeEvent.setResume(Resume.builder().id("123").localFileSystemPath("localFile").build());
        return resumeEvent;
    }
}