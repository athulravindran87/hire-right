package com.hright.processor.disruptor.processor;

import com.hright.processor.disruptor.bean.BeanArray;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.config.DisruptorProperties;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class DisruptorProcessor {

    @Getter
    private Disruptor<ResumeEvent> myEventDisruptor;

    @Autowired
    public DisruptorProcessor(@Qualifier("documentPublishHandler") BeanArray<ResumeEvent> publishHandler,
                              @Qualifier("pdfParserHandler") BeanArray<ResumeEvent> pdfParserHandler,
                              @Qualifier("sendToDownstreamHandler") BeanArray<ResumeEvent> sendDownStreamHandler,
                              DisruptorProperties properties) {

        this.myEventDisruptor = new Disruptor<>(ResumeEvent::new, properties.getBufferSize(), Executors.defaultThreadFactory(), ProducerType.SINGLE, new SleepingWaitStrategy());
        this.configureHandlers(publishHandler, pdfParserHandler, sendDownStreamHandler);

    }

    private void configureHandlers(BeanArray<ResumeEvent> publishHandler, BeanArray<ResumeEvent> pdfParserHandler,
                                   BeanArray<ResumeEvent> sendDownStreamHandler) {

        this.myEventDisruptor.handleEventsWithWorkerPool(publishHandler.getHandler())
                .thenHandleEventsWithWorkerPool(pdfParserHandler.getHandler())
                .thenHandleEventsWithWorkerPool(sendDownStreamHandler.getHandler());
    }
}
