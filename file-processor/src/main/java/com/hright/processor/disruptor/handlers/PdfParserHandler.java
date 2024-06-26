package com.hright.processor.disruptor.handlers;

import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.parser.service.ParserService;
import com.lmax.disruptor.WorkHandler;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Objects;

@Component
@Slf4j
public class PdfParserHandler implements WorkHandler<ResumeEvent> {

    @Autowired
    private ParserService parserService;

    @Override
    @Timed(value = "pdf_parser", description = "pdf parser", longTask = true)
    public void onEvent(ResumeEvent resumeEvent) throws Exception {
        try {
            if (Objects.isNull(resumeEvent)) {
                log.error("Event is null, cannot proceed");
                return;
            }
            Resume resume = resumeEvent.getResume();

            if (Objects.isNull(resume.getLocalFileSystemPath())) {
                log.error("File System url is empty for Resume id: {}, cannot proceed", resume.getId());
                return;
            }
            log.info("Performing OCR for resume: {}", resume.getId());
            StopWatch watch = new StopWatch();
            watch.start();

            this.parserService.parse(resume);

            watch.stop();
            log.info("Time taken to process resume: {} is {}", resume.getId(), watch.getTotalTimeSeconds());

        } catch (Exception e) {
            log.error("Unable to parse PDF document for id: {}", resumeEvent.getResume().getId());
        }
    }
}
