package com.douzone.kafka.controller;

//import com.douzone.kafka.producer.CallbackProducer;
//import com.douzone.kafka.producer.Producer;

import com.douzone.kafka.model.Result;
import com.douzone.kafka.model.Student;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class KafkaSenderController {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ReplyingKafkaTemplate<String, Student, Result> replyingKafkaTemplate;
    private KafkaTemplate<String, Student> kafkaTemplate;

    @Autowired
    public KafkaSenderController(@Qualifier("replyingKafkaTemplate") ReplyingKafkaTemplate<String, Student, Result> replyingKafkaTemplate,
                                 @Qualifier("requestTemplate") KafkaTemplate<String, Student> kafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/sync")
    public ResponseEntity<Result> sync()
            throws InterruptedException, ExecutionException {
        ProducerRecord<String, Student> record = new ProducerRecord<>("request", null, "dd", Student.builder().name("홍길동").grade("900").build());

        RequestReplyFuture<String, Student, Result> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Result> response = future.get();
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }

    @GetMapping("/async")
    public ResponseEntity<String> async(@RequestParam(value = "name", required = false) String name) {
        ProducerRecord<String, Student> record = new ProducerRecord<>("request", null, "dd", Student.builder().name(name).grade("900").build());

        logger.info(dateFormat.format(new Date()) + "============= start " + name);
        kafkaTemplate.send(record);
        return new ResponseEntity<>("비동기 전달 완료", HttpStatus.OK);
    }
//    @GetMapping("/sync")
//    public String sendMessage(@RequestParam("message") String str) {
//        ProducerRecord<String, String> message = new ProducerRecord<>("topic", str);
//
//        RequestReplyFuture<String, String, String> future =
//                replyingKafkaTemplate.sendAndReceive(message);
//
//        try {
//            // 응답을 받을 때까지 대기합니다.
//            ConsumerRecord<String, String> record = future.get();
//
//            // 받은 응답을 출력합니다.
//            String replyMessage = record.value();
//            System.out.println("Received reply: " + replyMessage);
//
//            return "Sent message: " + message + ", Received reply: " + replyMessage;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error occurred while sending/receiving message";
//        }
//
//    }
    // @GetMapping("/sync")
//    public String sync() {
//        callbackProducer.send("topic", MessageModel.builder().deptNm("회사명").deptCd("async").hasNext(5).build());
//        return "sync OK";
//    }
}