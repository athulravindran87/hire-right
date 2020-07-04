package com.hright.processor.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Slf4j
public class AppConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        log.info("Registering Meter Registry for application {}", this.appName);
        return registry -> registry.config().commonTags("application", this.appName);
    }

    @Bean
    TimedAspect timedAspect(MeterRegistry meterRegistry) {
        log.info("Registering Timed Aspect for application {}", this.appName);
        return new TimedAspect(meterRegistry);
    }
}
