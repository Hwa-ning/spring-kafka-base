package com.douzone.kafka.producer;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class CallbackProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SendResult<String, Object> send(String topic, Object data) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, data);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new DzListenableFutureCallback(data, record));

        try {
            return future.get();
        } catch (Exception e) {
            return null;
        }
    }
}
