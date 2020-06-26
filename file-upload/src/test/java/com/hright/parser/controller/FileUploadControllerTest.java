package com.hright.parser.controller;

import com.hright.parser.service.FileUploadService;
import com.hright.test.BaseTest;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @InjectMocks
    private FileUploadController testObj;

    @Mock
    private FileUploadService service;

    private MockMvc mockMvc;

    private File tempFolder;

    @Before
    public void setUp() throws Exception {
        tempFolder = temporaryFolder.newFolder();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.testObj).build();
        when(this.service.process(any())).thenReturn(Lists.mutable.of(new File("abc")));
    }

    @After
    public void tearDown() throws Exception {
        temporaryFolder.delete();
    }

    @Test
    public void testParseZipFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "Archive.zip",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        assertThat(this.testObj.parse(file).getBody(), equalTo("File Upload successful for total of 1 file(s)"));
    }

    @Test
    public void testParsePdfFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.pdf",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        assertThat(this.testObj.parse(file).getBody(), equalTo("File Upload successful for total of 1 file(s)"));
    }

    @Test
    public void testParseFileWithIncorrectExtn() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.xxx",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        assertThat(this.testObj.parse(file).getBody(), equalTo("Only zip and pdf are accepted"));

    }

    @Test
    public void testMockMvcHappyPathZipFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "Archive.zip",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        this.mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File Upload successful for total of 1 file(s)"));
    }

    @Test
    public void testMockMvcHappyPathPdfFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.pdf",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        this.mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File Upload successful for total of 1 file(s)"));
    }

    @Test
    public void testMockMvcHappyPathIncorrectExtn() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.xxx",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        this.mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only zip and pdf are accepted"));
    }
}