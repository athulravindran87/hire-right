package com.hright.processor.disruptor.config;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class DisruptorPropertiesTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testProperties() {
        DisruptorProperties disruptorProperties = new DisruptorProperties();
        disruptorProperties.setBufferSize(100);
        DisruptorProperties.Handler handler = new DisruptorProperties.Handler();
        handler.setDocumentPublishHandlerCount(1);
        handler.setPdfParserHandlerCount(2);
        handler.setSendDownStreamHandlerCount(3);
        disruptorProperties.setHandler(handler);

        assertThat(disruptorProperties.getBufferSize(), equalTo(100));
        assertThat(disruptorProperties.getHandler().getDocumentPublishHandlerCount(), equalTo(1));
        assertThat(disruptorProperties.getHandler().getPdfParserHandlerCount(), equalTo(2));
        assertThat(disruptorProperties.getHandler().getSendDownStreamHandlerCount(), equalTo(3));
    }
}