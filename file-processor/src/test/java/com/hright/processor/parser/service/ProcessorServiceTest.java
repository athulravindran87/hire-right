package com.hright.processor.parser.service;

import com.hright.model.Resume;
import com.hright.processor.disruptor.producer.EventProducer;
import com.hright.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorServiceTest extends BaseTest {

    @Mock
    private EventProducer producer;

    @InjectMocks
    private ProcessorService service;

    @Test
    public void process() {
        Resume resume = Resume.builder().id("id1").localFileSystemPath("local").build();
        this.service.process(resume);
        verify(this.producer).publishEvent(resume);
    }
}