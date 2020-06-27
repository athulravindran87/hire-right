package com.hright.processor.disruptor.handlers;

import com.hright.FileService;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocumentPublishHandler implements WorkHandler<ResumeEvent> {

    @Autowired
    private FileService fileService;

    @Override
    public void onEvent(ResumeEvent resumeEvent) throws Exception {
        this.fileService.save(resumeEvent.getResume());
        log.info("Resume Data saved: {}", resumeEvent.getResume());
    }
}
