package com.hright.processor.parser.service;

import com.hright.FileService;
import com.hright.model.Resume;
import com.hright.processor.parser.tess4j.Tess4jParser;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParserServiceTest extends BaseTest {

    @Mock
    private Tess4jParser parser;

    @Mock
    private FileService fileService;

    @InjectMocks
    private ParserService testObj;

    @Mock
    private File mockFile;

    @Before
    public void setUp() throws Exception {
        when(this.fileService.retrieveFile(anyString())).thenReturn(mockFile);
        when(this.parser.parse(mockFile)).thenReturn("somebody");
    }

    @Test
    public void testParse() throws Exception {
        Resume requestObj = Resume.builder().id("ID").resumeUrl("someurl").build();
        this.testObj.parse(requestObj);
        assertThat(requestObj.getBody(), equalTo("somebody"));
    }
}