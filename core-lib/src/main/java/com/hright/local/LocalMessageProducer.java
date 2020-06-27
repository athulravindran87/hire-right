package com.hright.local;

import com.hright.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.consumer.enabled", havingValue = "false")
public class LocalMessageProducer implements MessageProducer {
    @Override
    public void sendMessage(String topic, String key, String message) {
        log.info("Sending Message");
    }
}
