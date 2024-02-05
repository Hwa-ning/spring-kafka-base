//package com.douzone.kafka.consumer;
//
//import com.douzone.kafka.model.MessageModel;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class KafkaConsumerService {
//
////    private final KafkaTemplate<String, Object> kafkaTemplate;
//
////    @Autowired
////    public KafkaConsumerService(KafkaTemplate<String, Object> kafkaTemplate) {
////        this.kafkaTemplate = kafkaTemplate;
////    }
//
//    @KafkaListener(topics = "kRequests", groupId = "DMS", concurrency = "1")
//    @SendTo("kReplies")
//    public Message<?> consumeSYNC(ConsumerRecord<String, MessageModel> record) {
//
//        System.out.println("Thread Sleep 전");
//        try {
//            Thread.sleep(1500L);
//        } catch (Exception e) {
//            System.out.println("터짐" + e.getMessage());
//        }
//
//        record.value().setCompleteDate(new Date().toString());
//        record.value().setDeptNm("변경 완료");
//        return MessageBuilder.withPayload(record.value())
//                .build();
////        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("SCM_", record.key(), record.value());
////        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
////            @Override
////            public void onSuccess(SendResult<String, Object> result) {
////                System.out.println("onSuccess");
////            }
////
////            @Override
////            public void onFailure(Throwable ex) {
////                System.out.println("onFailure");
////                // Producer에 메시지 전송 실패
////            }
////        });
//    }
////
////    @SendTo
////    @KafkaListener(topics = "SYNC__", groupId = "DMS", concurrency = "1")
////    public void consumeSYNC(ConsumerRecord<String, MessageModel> record, Acknowledgment ack) {
////        try {
////            System.out.println("대충 무슨 요청해서 값 가져오는 중");
////
////            Thread.sleep(5000);
////            System.out.println("consume runAsnyc / record.value : " + record.value().getDeptNm());
////
////            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("SCM_", record.key(), record.value());
////            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
////                @Override
////                public void onSuccess(SendResult<String, Object> result) {
////                    System.out.println("onSuccess");
////                }
////
////                @Override
////                public void onFailure(Throwable ex) {
////                    System.out.println("onFailure");
////                    // Producer에 메시지 전송 실패
////                }
////            });
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////    }
//}