package com.hright.processor.disruptor.config;

import com.hright.processor.disruptor.bean.BeanArray;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.handlers.DocumentPublishHandler;
import com.hright.processor.disruptor.handlers.PdfParserHandler;
import com.hright.processor.disruptor.handlers.SendDownStreamHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {

    @Autowired
    private DisruptorProperties disruptorProperties;

    @Bean("documentPublishHandler")
    public BeanArray<ResumeEvent> publishHandler(ObjectProvider<DocumentPublishHandler> objectProvider) {
        return BeanArray.createBeanArray(objectProvider::getObject, disruptorProperties.getHandler().getDocumentPublishHandlerCount());
    }

    @Bean("pdfParserHandler")
    public BeanArray<ResumeEvent> parserHandler(ObjectProvider<PdfParserHandler> objectProvider) {
        return BeanArray.createBeanArray(objectProvider::getObject, disruptorProperties.getHandler().getPdfParserHandlerCount());
    }

    @Bean("sendToDownstreamHandler")
    public BeanArray<ResumeEvent> sendToDownStreamHandler(ObjectProvider<SendDownStreamHandler> objectProvider) {
        return BeanArray.createBeanArray(objectProvider::getObject, disruptorProperties.getHandler().getSendDownStreamHandlerCount());
    }
}
