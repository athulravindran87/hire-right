package com.hright.parser.controller;

import com.hright.file.FileService;
import com.hright.test.BaseTest;
import org.apache.commons.io.FileUtils;
import org.eclipse.collections.api.list.MutableList;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileUploadController testObj;

    private MockMvc mockMvc;

    private File tempFolder;

    @Before
    public void setUp() throws Exception {
        tempFolder = temporaryFolder.newFolder();
        ReflectionTestUtils.setField(this.testObj, "tempFolder", tempFolder.getAbsolutePath());
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.testObj).build();
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

        this.testObj.parse(file);

        verify(this.fileService).saveAll(anyList());
    }

    @Test
    public void testParsePdfFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.pdf",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        this.testObj.parse(file);

        verify(this.fileService).saveAll(anyList());
    }

    @Test
    public void testParseFileWithIncorrectExtn() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.xxx",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        this.testObj.parse(file);

        verify(this.fileService, never()).saveAll(anyList());
    }

    @Test
    public void testProcessOriginal() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "Archive.zip",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        File subDir = this.testObj.processOriginal(file);
        assertThat(FileUtils.listFiles(subDir, new String[]{"zip"}, true), hasSize(1));
    }

    @Test
    public void testProcessExtractZip() throws Exception {
        FileUtils.copyFileToDirectory(ResourceUtils.getFile("classpath:testdata/Archive.zip"), new File(tempFolder.getAbsolutePath()));
        MutableList<File> files = this.testObj.extractFilesFromZip(tempFolder.getAbsolutePath(), "Archive.zip");
        assertThat(files, hasSize(5));
    }

    @Test
    public void testMockMvcHappyPathZipFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "Archive.zip",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        this.mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File Upload successful for total of 5 file(s)"));
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

    @Test
    public void testMockMvcException() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.pdf",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        when(this.fileService.saveAll(anyList())).thenThrow(new RuntimeException());

        this.mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unable to parse multipart file invoice.pdf"));
    }
}