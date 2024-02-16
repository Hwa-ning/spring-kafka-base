//package com.douzone.kafka;
//
//import com.douzone.kafka.producer.ProducerService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Slf4j
//@SpringBootTest
//class SpringKafkaClientApplicationTests {
//    @Autowired
//    ProducerService producerService;
//
//    @Test
//    void kafkaRequestReply_test() {
//        String request = "abcd123";
//        String mustResponse = "321dcba";
//        Object sendReply = producerService.req("send", request);
//        String responseString = String.valueOf(sendReply);
//        assertEquals(mustResponse, responseString);
//        log.info("Request message: {}, Response message: {}", request, responseString);
//    }
//
//    @Test
//    void kafkaAsync_test() {
//        String req = "abc";
//
//        producerService.asyncReq("asnyc", req);
//
//        String abc = "";
//        assertEquals("", abc);
//
//    }
//}