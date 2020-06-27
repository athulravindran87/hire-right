package com.hright.processor.disruptor.handlers;

import com.hright.FileService;
import com.hright.model.Resume;
import com.hright.processor.disruptor.bean.ResumeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DocumentPublishHandlerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private DocumentPublishHandler testObj;

    @Test
    public void onEvent() throws Exception {
        ResumeEvent event = this.createEvent();

        this.testObj.onEvent(event);

        verify(this.fileService).save(event.getResume());
    }

    private ResumeEvent createEvent() {
        ResumeEvent resumeEvent = new ResumeEvent();
        resumeEvent.setSequenceId(123L);
        resumeEvent.setResume(Resume.builder().id("123").localFileSystemPath("localFile").build());
        return resumeEvent;
    }
}