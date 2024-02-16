package com.douzone.kafka.config;

import com.douzone.kafka.model.Result;
import com.douzone.kafka.model.Student;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean("replyingKafkaTemplate")
    public ReplyingKafkaTemplate<String, Student, Result> replyingKafkaTemplate(ProducerFactory<String, Student> studentProducerFactory,
                                                                                ConcurrentKafkaListenerContainerFactory<String, Result> factory) {
        ConcurrentMessageListenerContainer<String, Result> replyContainer = factory.createContainer("reply");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("group");
        return new ReplyingKafkaTemplate<>(studentProducerFactory, replyContainer);
    }

    @Bean("replyTemplate")
    public KafkaTemplate<String, Result> replyTemplate(ProducerFactory<String, Result> resultProducerFactory,
                                                       ConcurrentKafkaListenerContainerFactory<String, Result> factory) {
        KafkaTemplate<String, Result> kafkaTemplate = new KafkaTemplate<>(resultProducerFactory);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean("requestTemplate")
    public KafkaTemplate<String, Student> requestTemplate() {
        return new KafkaTemplate<>(studentProducerFactory());
    }

    @Bean("studentListenerContainerFactory")
    @Primary
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Student>> studentListenerContainerFactory(
            ConsumerFactory<String, Student> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Student> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }

    @Bean
    public ProducerFactory<String, Result> resultProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, Student> studentProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ConsumerFactory<String, Student> studentConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }

    private Map<String, Object> producerConfig() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return producerConfig;
    }

    private Map<String, Object> consumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 가장 이른 오프셋부터 consume, 컨슈머가 해당 토픽의 파티션에 대해 이전에 컨슈밍한 기록이 없으면 가장 초기부터 메시지를 소비합니다.
        return consumerConfig;
    }
}