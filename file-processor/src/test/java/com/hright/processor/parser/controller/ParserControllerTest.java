package com.hright.processor.parser.controller;

import com.google.gson.Gson;
import com.hright.model.Resume;
import com.hright.processor.parser.service.ParserService;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class ParserControllerTest extends BaseTest {

    public static final String ARBITRARY_ID = "123";
    public static final String ARBITRARY_BODY = "somebody";
    public static final String SOMEURL = "http://someurl";

    @Mock
    private ParserService parserService;

    @InjectMocks
    private ParserController testObj;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.testObj).build();
    }

    @Test
    public void parse() {
        Resume resume = Resume.builder().id(ARBITRARY_ID).body(ARBITRARY_BODY).resumeUrl(SOMEURL).build();
        ResponseEntity<String> result = this.testObj.parse(resume);
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), containsString("Success"));
        verify(this.parserService).parse(eq(resume));
    }

    @Test
    public void parseMockMvc() throws Exception {
        Resume resume = Resume.builder().id(ARBITRARY_ID).body(ARBITRARY_BODY).resumeUrl(SOMEURL).build();
        this.mockMvc.perform(post("/parse")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(resume)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void parseNull() {
        ResponseEntity<String> result = this.testObj.parse(null);
        assertThat(result.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody(), containsString("Bad Request"));
        verify(this.parserService, never()).parse(any());
    }

    @Test
    public void parseMockMvcNull() throws Exception {
        Resume resume = Resume.builder().id(null).body(ARBITRARY_BODY).resumeUrl(SOMEURL).build();
        this.mockMvc.perform(post("/parse")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(resume)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Bad Request"));
    }
}