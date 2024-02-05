package com.douzone.kafka.consumer;

import com.douzone.kafka.model.MessageModel;
import com.douzone.kafka.producer.CallbackProducer;
import com.douzone.kafka.producer.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DBConsumer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final Producer producer;
    private final CallbackProducer callbackProducer;
    private final Logger logger = LoggerFactory.getLogger(DBConsumer.class);
//    private final com.douzone.comet.service.oss.integration.adapter.database.DatabaseConsumerService databaseConsumerService;

    @KafkaListener(groupId = "DMS", topics = "topic")
    public void consume(ConsumerRecord<String, String> record) {
        MessageModel msg = null;
        try {
            msg = mapper.readValue(record.value(), new TypeReference<MessageModel>() {
            });

            logger.info("[consume 수신] model : " + msg);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (msg.getHasNext() > 0) {
            msg.setHasNext(msg.getHasNext() - 1);
            msg.setDeptNm(msg.getDeptNm() + " " + msg.getHasNext());
            callbackProducer.send("topic", msg);
        } else {
            for (int i = 0; i < 5; i++) {
                logger.info("=====================================================================");
            }
        }
    }

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
}
