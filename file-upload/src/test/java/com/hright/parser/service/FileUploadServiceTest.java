package com.hright.parser.service;

import com.hright.parser.messaging.MessageSender;
import com.hright.test.BaseTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.collections.api.list.MutableList;
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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadServiceTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File tempFolder;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private FileUploadService testObj;

    @Before
    public void setUp() throws Exception {
        tempFolder = temporaryFolder.newFolder();
        ReflectionTestUtils.setField(this.testObj, "tempFolder", tempFolder.getAbsolutePath());
        doNothing().when(this.messageSender).sendMessage(any());
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
    public void testProcessSinglePdf() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "invoice.pdf",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/invoice.pdf")));

        MutableList<File> files = this.testObj.process(file);
        assertThat(files, hasSize(1));
    }

    @Test
    public void testProcessSingleDocx() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "test3.docx",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/test3.docx")));

        MutableList<File> files = this.testObj.process(file);
        assertThat(files, hasSize(1));
        assertThat(FilenameUtils.getExtension(files.get(0).getName()), equalTo("pdf"));
    }

    @Test
    public void testProcessSingleDoc() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "test2.doc",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/test2.doc")));

        MutableList<File> files = this.testObj.process(file);
        assertThat(files, hasSize(1));
        assertThat(FilenameUtils.getExtension(files.get(0).getName()), equalTo("pdf"));
    }

    @Test
    public void testProcessZip() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "Archive.zip",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        new FileInputStream(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        MutableList<File> files = this.testObj.process(file);
        assertThat(files, hasSize(5));
    }
}