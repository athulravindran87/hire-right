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
import org.mockito.Answers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WorkHandler.class})
public class DisruptorProcessorTest extends BaseTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private BeanArray<ResumeEvent> resumeEventBeanArray;

    @Mock
    private DisruptorProperties properties;

    private WorkHandler mockHandler;

    private DisruptorProcessor testObj;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(WorkHandler.class);
        when(this.properties.getBufferSize()).thenReturn(1024);
        when(this.resumeEventBeanArray.getHandler()).thenReturn(new WorkHandler[]{mockHandler, mockHandler});
        this.testObj = new DisruptorProcessor(resumeEventBeanArray, resumeEventBeanArray, resumeEventBeanArray, properties);
    }

    @Test
    public void testGetMyEventDisruptor() {
        Disruptor<ResumeEvent> myEventDisruptor = this.testObj.getMyEventDisruptor();
        assertThat(myEventDisruptor.getBufferSize(), equalTo(1024L));
    }

}