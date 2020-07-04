package com.hright.processor.config;

import com.hright.test.BaseTest;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest extends BaseTest {

    @InjectMocks
    private AppConfig testObj;

    @Mock
    private MeterRegistry registry;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(this.testObj, "appName", "myApp");
    }

    @Test
    public void metricsCommonTags() {
        MeterRegistryCustomizer<MeterRegistry> meterRegistry = this.testObj.metricsCommonTags();
        assertNotNull(meterRegistry);
    }

    @Test
    public void timedAspect() {
        assertNotNull(this.testObj.timedAspect(registry));
    }
}