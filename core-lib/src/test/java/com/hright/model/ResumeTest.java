package com.hright.model;


import com.hright.test.BaseTest;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

public class ResumeTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File tempFolder;

    @Before
    public void setUp() throws Exception {
        tempFolder = temporaryFolder.newFolder();
    }

    @Test
    public void testPojoStructureAndBehavior() {

        PojoClass resume = PojoClassFactory.getPojoClass(Resume.class);

        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .with(new NoStaticExceptFinalRule())
                .with(new SerializableMustHaveSerialVersionUIDRule())
                .with(new TestClassMustBeProperlyNamedRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(resume);
    }

    @Test
    public void testTo() {
        File file = new File(tempFolder.getAbsolutePath() + "/abc.txt");
        Resume to = Resume.to(file);
        assertThat(to.getId(), containsString("R-" + LocalDate.now()));
        assertThat(to.getLocalFileSystemPath(), equalTo(tempFolder.getAbsolutePath() + "/" + "abc.txt"));
        assertThat(to.getCreateDate(), notNullValue());
        assertNull(to.getBody());
        assertNull(to.getResumeUrl());
    }

    @Test
    public void testJson() {
        String path = tempFolder.getAbsolutePath() + "/" + "abc.txt";
        String date = LocalDate.now(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ISO_DATE);
        File file = new File(path);
        Resume to = Resume.to(file);
        assertThat(to.toJson(), containsStringIgnoringCase("\"id\":\"R-" + LocalDate.now()));
        assertThat(to.toJson(), containsStringIgnoringCase("\"body\":null"));
        assertThat(to.toJson(), containsStringIgnoringCase("\"resumeUrl\":null"));
        assertThat(to.toJson(), containsStringIgnoringCase("\"createDate\":\"" + date));
        assertThat(to.toJson(), containsStringIgnoringCase("\"localFileSystemPath\":\"" + path));
    }
}