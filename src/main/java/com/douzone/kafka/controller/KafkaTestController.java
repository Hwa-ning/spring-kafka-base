package com.douzone.kafka.controller;

import com.douzone.kafka.model.MessageModel;
import com.douzone.kafka.producer.CallbackProducer;
import com.douzone.kafka.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {
    private final Producer producer;
    private final CallbackProducer callbackProducer;

    @GetMapping("/async")
    public String send() {
        producer.send("topic", MessageModel.builder().deptNm("회사명").deptCd("async").hasNext(5).build());
        return "good";
    }

    @GetMapping("/sync")
    public String async() {
        callbackProducer.send("topic", MessageModel.builder().deptNm("회사명").deptCd("async").hasNext(5).build());

        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        return "async OK";
    }
}