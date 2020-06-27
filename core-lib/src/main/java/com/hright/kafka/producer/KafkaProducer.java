package com.hright.kafka.producer;

import com.hright.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.consumer.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaProducer implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, String key, String message) {
        log.info("Sending Message to topic {}, key: {}, message: {}", topic, key, message);
        this.kafkaTemplate.send(topic, key, message);
        this.kafkaTemplate.flush();
    }
}
