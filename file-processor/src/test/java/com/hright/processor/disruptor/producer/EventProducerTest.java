package com.hright.processor.disruptor.producer;

import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.processor.DisruptorProcessor;
import com.hright.test.BaseTest;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventProducerTest extends BaseTest {

    @Mock
    private DisruptorProcessor disruptorProcessor;

    @Mock
    private Disruptor<ResumeEvent> disruptor;

    @Mock
    private RingBuffer<ResumeEvent> ringBuffer;

    private EventProducer testObj;

    private Resume resume;

    @Before
    public void setUp() throws Exception {

        when(this.disruptorProcessor.getMyEventDisruptor()).thenReturn(this.disruptor);
        when(this.disruptor.start()).thenReturn(this.ringBuffer);
        //PowerMockito.mockStatic(RingBuffer.class);

        this.resume = Resume.builder()
                .id("testId")
                .resumeUrl("someUrl")
                .build();

        this.testObj = new EventProducer(this.disruptorProcessor);
    }

    @Test
    public void publishEvent() {

        this.testObj.publishEvent(this.resume);
        verify(this.ringBuffer).publishEvent(eq(EventProducer.TRANSLATOR_ONE_ARG), eq(this.resume));
    }

    @Test
    public void translate() {
        ResumeEvent resumeEvent = new ResumeEvent();
        EventProducer.translate(resumeEvent, 100L, this.resume);
        assertThat(resumeEvent.getSequenceId(), equalTo(100L));
        assertThat(resumeEvent.getResume(), equalTo(this.resume));
    }
}