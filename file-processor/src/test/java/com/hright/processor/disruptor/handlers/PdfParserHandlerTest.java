package com.hright.processor.disruptor.handlers;

import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import com.hright.processor.parser.service.ParserService;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PdfParserHandlerTest extends BaseTest {

    @Mock
    private ParserService parserService;

    @InjectMocks
    private PdfParserHandler testObj;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onEvent() throws Exception {
        this.testObj.onEvent(this.createEvent());
        verify(this.parserService).parse(this.createEvent().getResume());
    }

    @Test
    public void onEventNullEvent() throws Exception {
        this.testObj.onEvent(null);
        verify(this.parserService, never()).parse(this.createEvent().getResume());
    }

    @Test
    public void onEventNoUrl() throws Exception {
        Resume resume = this.createEvent().getResume();
        resume.setResumeUrl("");

        this.testObj.onEvent(this.createEvent());
        verify(this.parserService, never()).parse(resume);
    }

    @Test
    public void onEventException() throws Exception {

        doThrow(new RuntimeException()).when(this.parserService).parse(any());

        this.testObj.onEvent(this.createEvent());
        verify(this.parserService).parse(this.createEvent().getResume());
    }

    private ResumeEvent createEvent() {
        ResumeEvent resumeEvent = new ResumeEvent();
        resumeEvent.setSequenceId(123L);
        resumeEvent.setResume(Resume.builder()
                .id("RESUM123")
                .resumeUrl("local")
                .build());
        return resumeEvent;
    }
}