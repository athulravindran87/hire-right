package com.hright.remote;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.hright.model.Resume;
import com.hright.test.BaseTest;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AwsFileServiceTest extends BaseTest {

    @Mock
    private AmazonS3Client s3Client;

    @Mock
    private AwsConfig awsConfig;

    @InjectMocks
    private AwsFileService tesObj;

    @Before
    public void setUp() throws Exception {
        when(this.awsConfig.getBucketName()).thenReturn("resume-bucket");
        when(this.s3Client.putObject(any())).thenReturn(new PutObjectResult());
        when(this.s3Client.getUrl("resume-bucket", "file1")).thenReturn(new URL("http://s3/dummy"));
        when(this.s3Client.getUrl("resume-bucket", "file2")).thenReturn(new URL("http://s3/dummy2"));
    }

    @Test
    public void retrieveFile() {
        assertThat(this.tesObj.retrieveFile("some"), instanceOf(File.class));
    }

    @Test
    public void save() {
        Resume resume = Resume.builder().localFileSystemPath("/somewhere/file1").build();
        this.tesObj.save(resume);
        assertThat(resume.getResumeUrl(), equalTo("http://s3/dummy"));
        verify(this.s3Client).putObject(any());
        verify(this.s3Client).getUrl(any(), anyString());
    }

    @Test
    public void saveAll() {
        MutableList<Resume> resumes = Lists.mutable.of(Resume.builder().localFileSystemPath("/somewhere/file1").build(),
                Resume.builder().localFileSystemPath("/somewhere/file2").build());

        this.tesObj.saveAll(resumes);
        verify(this.s3Client, times(2)).putObject(any());
        verify(this.s3Client, times(2)).getUrl(any(), anyString());
    }
}