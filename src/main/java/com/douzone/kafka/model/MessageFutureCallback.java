package com.douzone.kafka.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class MessageFutureCallback implements ListenableFutureCallback<SendResult<String, Object>> {
    private final MessageModel message;
    private final Logger logger = LoggerFactory.getLogger(MessageFutureCallback.class);

    public MessageFutureCallback(MessageModel message) {
        this.message = message;
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
    }

    @Override
    public void onSuccess(SendResult<String, Object> result) {
        logger.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
    }
}
