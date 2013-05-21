package com.example.dropwizardtestserver.core;

public class AutomatedRequestQueueConfiguration {
    private String brokerURL;
    private boolean transacted;
    private int acknowledgeMode;
    private String queueName;

    public String getBrokerURL() {
        return brokerURL;
    }

    public boolean isTransacted() {
        return transacted;
    }

    public int getAcknowledgeMode() {
        return acknowledgeMode;
    }

    public String getQueueName() {
        return queueName;
    }
}
