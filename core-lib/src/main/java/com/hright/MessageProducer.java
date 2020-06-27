package com.hright;

public interface MessageProducer {
    void sendMessage(String topic, String key, String message);
}
