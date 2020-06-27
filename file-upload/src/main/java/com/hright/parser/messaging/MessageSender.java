package com.hright.parser.messaging;

import com.hright.MessageProducer;
import com.hright.model.Resume;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class MessageSender {

    @Value("{kafka.topic.file-processor}")
    private String topic;

    @Autowired
    private MessageProducer producer;

    public void sendMessage(MutableList<File> files) {
        resumes(files)
                .tap(resume -> log.info("Sending Message: {}", resume))
                .forEach(resume -> this.producer.sendMessage(topic, resume.getId(), resume.toJson()));
    }

    protected MutableList<Resume> resumes(MutableList<File> files) {
        return files.collect(Resume::to);
    }
}
