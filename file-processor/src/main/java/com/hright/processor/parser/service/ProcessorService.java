package com.hright.processor.parser.service;

import com.hright.model.Resume;
import com.hright.processor.disruptor.producer.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessorService {

    @Autowired
    private EventProducer eventProducer;

    public void process(Resume resume) {
        this.eventProducer.publishEvent(resume);
    }
}
