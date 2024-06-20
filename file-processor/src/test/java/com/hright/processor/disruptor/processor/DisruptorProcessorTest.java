package com.hright.processor.disruptor.processor;

import com.hright.processor.disruptor.bean.BeanArray;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.config.DisruptorProperties;
import com.hright.test.BaseTest;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DisruptorProcessorTest extends BaseTest {

    @Mock
    private BeanArray<ResumeEvent> resumeEventBeanArray;

    @Mock
    private DisruptorProperties properties;

    @Mock
    private WorkHandler<ResumeEvent> mockHandler;

    private DisruptorProcessor testObj;

    @Before
    public void setUp() throws Exception {
        Mockito.mockStatic(WorkHandler.class);
        when(this.properties.getBufferSize()).thenReturn(1024);
        when(this.resumeEventBeanArray.getHandler()).thenReturn(new WorkHandler[]{this.mockHandler, this.mockHandler});
        this.testObj = new DisruptorProcessor(resumeEventBeanArray, resumeEventBeanArray, resumeEventBeanArray, properties);
    }

    @Test
    public void testGetMyEventDisruptor() {
        Disruptor<ResumeEvent> myEventDisruptor = this.testObj.getMyEventDisruptor();
        assertThat(myEventDisruptor.getBufferSize(), equalTo(1024L));
    }

}