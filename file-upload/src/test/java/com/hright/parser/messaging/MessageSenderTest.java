package com.hright.parser.messaging;

import com.hright.MessageProducer;
import com.hright.model.Resume;
import com.hright.test.BaseTest;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MessageSenderTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private MessageSender testObj;

    private File tempFolder;
    private MutableList<File> files;

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(this.testObj, "topic", "test-topic");
        tempFolder = temporaryFolder.newFolder();
        this.createFiles();
    }

    @Test
    public void sendMessage() {
        this.testObj.sendMessage(this.files);
        verify(this.messageProducer, times(3)).sendMessage(eq("test-topic"), startsWith("R-" + LocalDate.now()), anyString());
    }

    @Test
    public void testResumes() {
        MutableList<Resume> resumes = this.testObj.resumes(this.files);
        assertThat(resumes, hasSize(3));
        assertThat(resumes, allOf(
                hasToString(containsString(tempFolder.getAbsolutePath() + "/" + "abc.txt")),
                hasToString(containsString(tempFolder.getAbsolutePath() + "/" + "bcd.txt")),
                hasToString(containsString(tempFolder.getAbsolutePath() + "/" + "bcd.txt"))
        ));
    }

    private MutableList<File> createFiles() {
        files = Lists.mutable.of(
                new File(tempFolder.getAbsolutePath() + "/" + "abc.txt"),
                new File(tempFolder.getAbsolutePath() + "/" + "bcd.txt"),
                new File(tempFolder.getAbsolutePath() + "/" + "def.txt"));
        return files;
    }
}