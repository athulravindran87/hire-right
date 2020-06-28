package com.hright.processor.disruptor.handlers;

import com.hright.MessageProducer;
import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendDownStreamHandler implements WorkHandler<ResumeEvent> {

    @Value(value = "${kafka.topic.elastic-search}")
    private String elasticSearchTopic;

    @Autowired
    private MessageProducer producer;

    @Override
    public void onEvent(ResumeEvent resumeEvent) throws Exception {
        Resume resume = resumeEvent.getResume();
        log.info("sending downstream:, {}", resume);
        this.producer.sendMessage(elasticSearchTopic, resume.getId(), resume.toJson());
    }
}
