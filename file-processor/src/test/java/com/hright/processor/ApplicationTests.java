package com.hright.processor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringApplication.class)
public class ApplicationTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private Application testObj;

    @Before
    public void setUp() {
        this.testObj = new Application();
        PowerMockito.mockStatic(SpringApplication.class);
    }

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(this.testObj);
    }

    @Test
    public void testApplicationRuns() throws Exception {
        String[] args = {"Some arg", "Some arg"};

        assertNotNull(testObj);

        Application.main(args);

        PowerMockito.verifyStatic(SpringApplication.class);

        SpringApplication.run(eq(Application.class), eq("Some arg"), eq("Some arg"));
    }
}
