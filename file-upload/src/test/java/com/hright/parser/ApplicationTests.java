package com.hright.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTests {

    @Mock
    private MockedStatic<SpringApplication> springApplication;

    private Application testObj;

    @Before
    public void setUp() {
        this.testObj = new Application();
    }

    @Test
    public void contextLoads() {
        assertNotNull(this.testObj);
    }

    @Test
    public void testApplicationRuns(){
        String[] args = {"Some arg", "Some arg"};

        Application.main(args);
        this.springApplication.verify(() -> SpringApplication.run(eq(Application.class), eq(args)));
    }
}
