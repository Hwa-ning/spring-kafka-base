package com.douzone.kafka.model;

public enum KafkaType {
    GW("GW_", "GwKafkaTemplate"),
    SCM("SCM_", "ScmKafkaTemplate"),
    SYNC("SYNC_", "AsyncKafkaTemplate");
    private final String prefix;
    private final String templateType;

    KafkaType(String prefix, String templateType) {
        this.prefix = prefix;
        this.templateType = templateType;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTemplateType() {
        return templateType;
    }
}
