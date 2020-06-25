package com.hright.processor.disruptor.handlers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DocumentPublishHandlerTest.class,
        PdfParserHandlerTest.class,
        SendDownStreamHandlerTest.class
})
public class AllHandlerTests {
}
