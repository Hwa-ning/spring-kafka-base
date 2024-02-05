//package com.douzone.kafka.consumer;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class ConsumerService {
//
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Autowired
//    ConsumerService(KafkaTemplate<String, Object> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @KafkaListener(groupId = "DMS", topics = "send")
//    @SendTo("reply")
//    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) {
//        String reversedString = new StringBuilder(String.valueOf(consumerRecord.value())).reverse().toString();
//        return MessageBuilder.withPayload(reversedString)
//                .build();
//    }
//
//    @KafkaListener(groupId = "DMS", topics = "async")
//    public void consumeAsnyc(ConsumerRecord<String, Object> consumerRecord) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        System.out.println(consumerRecord);
//
//        ResponseEntity res = restTemplate.exchange("http://localhost:5555", HttpMethod.GET, null, HttpEntity.class);
//
//        res.getBody();
//
//        kafkaTemplate.send("async", res);
//    }
//
//}