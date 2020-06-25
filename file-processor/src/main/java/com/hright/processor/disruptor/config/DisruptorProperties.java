package com.hright.processor.disruptor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "disruptor")
@Configuration
@Getter
@Setter
public class DisruptorProperties {

    private Handler handler = new Handler();
    private int bufferSize = 4096;

    @Getter
    @Setter
    public static class Handler {
        private int documentPublishHandlerCount = 5;
        private int pdfParserHandlerCount = 10;
        private int sendDownStreamHandlerCount = 5;
    }
}
