package com.hright.processor.disruptor.config;

import com.hright.processor.disruptor.bean.BeanArray;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.handlers.DocumentPublishHandler;
import com.hright.processor.disruptor.handlers.PdfParserHandler;
import com.hright.processor.disruptor.handlers.SendDownStreamHandler;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.ObjectProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DisruptorConfigTest extends BaseTest {

    @Mock
    private DisruptorProperties disruptorProperties;

    @Mock
    private DisruptorProperties.Handler handler;

    @InjectMocks
    private DisruptorConfig testObj;

    @Before
    public void setUp() throws Exception {
        when(this.disruptorProperties.getHandler()).thenReturn(this.handler);
        when(this.handler.getDocumentPublishHandlerCount()).thenReturn(1);
        when(this.handler.getPdfParserHandlerCount()).thenReturn(2);
        when(this.handler.getSendDownStreamHandlerCount()).thenReturn(3);
    }

    @Test
    public void publishHandler() {
        ObjectProvider<DocumentPublishHandler> objectProvider = Mockito.mock(ObjectProvider.class);
        when(objectProvider.getObject()).thenReturn(new DocumentPublishHandler());
        BeanArray beanArray = this.testObj.publishHandler(objectProvider);

        assertThat(beanArray.getHandler().length, equalTo(1));
    }

    @Test
    public void parserHandler() {
        ObjectProvider objectProvider = Mockito.mock(ObjectProvider.class);
        when(objectProvider.getObject()).thenReturn(new PdfParserHandler());
        BeanArray<ResumeEvent> beanArray = this.testObj.parserHandler(objectProvider);

        assertThat(beanArray.getHandler().length, equalTo(2));
    }

    @Test
    public void sendToDownStreamHandler() {
        ObjectProvider<SendDownStreamHandler> objectProvider = Mockito.mock(ObjectProvider.class);
        when(objectProvider.getObject()).thenReturn(new SendDownStreamHandler());
        BeanArray<ResumeEvent> beanArray = this.testObj.sendToDownStreamHandler(objectProvider);

        assertThat(beanArray.getHandler().length, equalTo(3));

    }
}