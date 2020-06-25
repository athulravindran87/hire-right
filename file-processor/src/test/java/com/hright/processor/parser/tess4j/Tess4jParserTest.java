package com.hright.processor.parser.tess4j;

import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class Tess4jParserTest extends BaseTest {

    private Tess4jParser testObj = new Tess4jParser();

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(this.testObj, "dataPath", "/Users/athul");
    }

    @Test
    public void parse() {

        File testFile = new File("/Users/athul/Athul Ravindran Resume.pdf");
        assertThat(testObj.parse(testFile), containsString("ATHUL RAVINDRAN\n" +
                "29304 Palm Ct, Lawrenceville, New Jersey 08648 USA @ (201) 423-3536 @ prasanna29387@gmail.com\n" +
                "Professional Summary"));
    }

    @Test
    public void parseWithException() {
        File testFile = new File("/Users/athul/SomeFile.pdf");
        assertThat(testObj.parse(testFile), equalTo(""));
    }
}