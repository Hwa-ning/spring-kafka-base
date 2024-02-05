package com.douzone.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object data) {
        kafkaTemplate.send(topic, data);
    }
}
