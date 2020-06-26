package com.hright.kafka.controller;

import com.hright.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @Autowired
    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String topic,
                                              @RequestParam String key,
                                              @RequestBody String message) {
        this.kafkaProducer.sendMessage(topic, key, message);
        return new ResponseEntity<String>("Message Sent", HttpStatus.OK);
    }
}
