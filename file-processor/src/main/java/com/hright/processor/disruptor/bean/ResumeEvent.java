package com.hright.processor.disruptor.bean;

import com.hright.model.Resume;
import lombok.Data;

@Data
public class ResumeEvent {
    private long sequenceId;
    private Resume resume;
}
