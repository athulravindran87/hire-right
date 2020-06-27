package com.hright.processor.kafka;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hright.model.Resume;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.consumer.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConsumer {

    @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = "${kafka.topic.file-processor}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("Recieved Message from partition: {} key: {}, message: {}", record.partition(), record.key(), record.value());
        try {
            Resume resume = new Gson().fromJson(record.value(), Resume.class);
        } catch (JsonSyntaxException e) {
            log.error("Error cannot convert to Resume Object");
        }
        acknowledgment.acknowledge();
    }
}
