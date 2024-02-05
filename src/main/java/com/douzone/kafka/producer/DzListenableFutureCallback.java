package com.douzone.kafka.producer;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class DzListenableFutureCallback implements ListenableFutureCallback<SendResult<String, Object>> {

    private final Object request;
    private final ProducerRecord<String, Object> record;

    private final Logger logger = LoggerFactory.getLogger(DzListenableFutureCallback.class);

    public DzListenableFutureCallback(Object request, ProducerRecord<String, Object> record) {
        this.request = request;
        this.record = record;
    }

    @Override
    public void onSuccess(SendResult<String, Object> result) {
        logger.info("[성공] " + result.getProducerRecord().topic() + " -> " + result.getProducerRecord().value());
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.error("[실패] " + request + " / " + record + " / " + ex);
    }
}