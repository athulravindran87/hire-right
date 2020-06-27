package com.hright.local;

import com.hright.model.Resume;
import com.hright.test.BaseTest;
import org.apache.commons.io.FileUtils;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class LocalFileServiceTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File tempFolder;

    private LocalFileService testObj;

    @Before
    public void setUp() throws Exception {
        this.testObj = new LocalFileService();
        tempFolder = temporaryFolder.newFolder();
    }

    @Test
    public void retrieveFile() throws Exception {
        FileUtils.copyFileToDirectory(ResourceUtils.getFile("classpath:testdata/Archive.zip"), new File(tempFolder.getAbsolutePath()));
        assertThat(this.testObj.retrieveFile(tempFolder.getAbsolutePath() + "/Archive.zip").getName(), equalTo("Archive.zip"));
    }

    @Test
    public void save() {
        Resume resume = Resume.builder().id("someId").localFileSystemPath("localPath").resumeUrl("").build();
        this.testObj.save(resume);
        assertThat(resume.getResumeUrl(), equalTo("localPath"));
    }

    @Test
    public void saveAll() {
        Resume resume = Resume.builder().id("someId1").localFileSystemPath("localPath1").resumeUrl("").build();
        Resume resume1 = Resume.builder().id("someId2").localFileSystemPath("localPath2").resumeUrl("").build();

        this.testObj.saveAll(Lists.mutable.of(resume1, resume));
        assertThat(resume.getResumeUrl(), equalTo("localPath1"));
        assertThat(resume1.getResumeUrl(), equalTo("localPath2"));
    }
}