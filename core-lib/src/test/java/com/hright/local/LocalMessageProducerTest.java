package com.hright.local;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class LocalMessageProducerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().mute();

    private LocalMessageProducer testObj = new LocalMessageProducer();

    @Test
    public void sendMessage() {
        this.testObj.sendMessage("topic1", "key1", "message1");
        assertThat(systemOutRule.getLog(), containsString("Sending Message"));
    }
}