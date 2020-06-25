package com.hright.processor.disruptor.producer;

import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.disruptor.processor.DisruptorProcessor;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventProducer {

    public static final EventTranslatorOneArg<ResumeEvent, Resume> TRANSLATOR_ONE_ARG = EventProducer::translate;

    private RingBuffer<ResumeEvent> ringBuffer;

    @Autowired
    public EventProducer(final DisruptorProcessor disruptorProcessor) {
        this.ringBuffer = disruptorProcessor.getMyEventDisruptor().start();
    }

    static void translate(ResumeEvent resumeEvent, long l, Resume resume) {
        resumeEvent.setResume(resume);
        resumeEvent.setSequenceId(l);
    }

    public void publishEvent(Resume resume) {
        this.ringBuffer.publishEvent(TRANSLATOR_ONE_ARG, resume);
    }
}
