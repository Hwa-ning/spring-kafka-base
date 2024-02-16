package com.douzone.kafka.consumer;

import com.douzone.kafka.model.Result;
import com.douzone.kafka.model.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "request", groupId = "group", containerFactory = "studentListenerContainerFactory")
    @SendTo
    public Result handle(@Payload Student student) {
        double total = ThreadLocalRandom.current().nextDouble(2.5, 9.9);

        try {
            logger.info(dateFormat.format(new Date()) + "############## start " + student.getName() + total);
            Thread.sleep(3000);
            logger.info(dateFormat.format(new Date()) + "############## end " + student.getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = (total / (double) 100) + "";

        return Result.builder().result((total > 5.0) ? "Pass" : "Fail")
                .percentage(result)
                .name("리턴값")
                .build();
    }
}

//    @KafkaListener(groupId = "DMS", topics = "topic", containerFactory = "kafkaListenerContainerFactory")
//    public void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
//        MessageModel msg = null;
//        try {
//            msg = mapper.readValue(record.value(), new TypeReference<MessageModel>() {
//            });
//
//            logger.info("[consume 수신] model : " + msg);
//
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage());
//        }
//        try {
//            Thread.sleep(5000);
//            System.out.println("5초 대기");
//            acknowledgment.acknowledge();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }

//    @KafkaListener(groupId = "DMS", topics = "top")
//    public void consumeTopic(ConsumerRecord<String, String> record) {
//
//        IntegrationMessage nowMsg = null;
//        IntegrationMessage nextMsg = null;
//        String integrationId = "";
//        // 1. JSON parsing
//        try {
//            nowMsg = mapper.readValue(record.value(), new TypeReference<IntegrationMessage>() {
//            });
//
//            logger.info("[consume 수신] model : " + nowMsg);
//
//            integrationId = nowMsg.getHeader().getIntegrationId();
//
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage());
//        }
//        // 2. 각 단계별로 맞는 요청 실행
//        try {
//            if (nowMsg != null) {
//                // dd
//                nextMsg = databaseConsumerService.consumeService(nowMsg);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        // 3-1. 다음 단계 진입
//        if (nextMsg.getHeader().getIntegrationId() != null) {
//            callbackProducer.send("nextTopic", nextMsg);
//        }
//        // 3-2. 존재하지 않는 경우
//        // 해당 flow 종료 및 flow에 대한 정보 저장
//        // hasNext == null 이니까 종료하는 로직 flow 종료에 대한 저장 필요할듯?
//        else { // 다음단계 존재하지 않는경우
//            // FLOW 인 경우
//            if ("flow".equals(nowMsg.getHeader().getCompanyCd())) { // TODO .getCompanyCd -> .getInterfaceType
//                // TODO logService.insertResult(nowMsg);
//            } else { // "SINGLE"인 경우
//                // TODO logService.insertResult(nowMsg);
//            }
//        }
//    }
