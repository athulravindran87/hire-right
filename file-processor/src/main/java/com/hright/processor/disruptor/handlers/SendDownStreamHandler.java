package com.hright.processor.disruptor.handlers;

import com.hright.processor.disruptor.bean.ResumeEvent;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendDownStreamHandler implements WorkHandler<ResumeEvent> {
    @Override
    public void onEvent(ResumeEvent resumeEvent) throws Exception {
        log.info("sending downstream:, {}", resumeEvent.getResume());
    }
}
