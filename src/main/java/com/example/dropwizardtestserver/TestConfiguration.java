package com.example.dropwizardtestserver;

import com.example.dropwizardtestserver.core.AutomatedRequestQueueConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class TestConfiguration extends Configuration {

    @JsonProperty
    private AutomatedRequestQueueConfiguration automatedRequestQueueConfiguration = new AutomatedRequestQueueConfiguration();

    public AutomatedRequestQueueConfiguration getAutomatedRequestQueueConfiguration() {
        return automatedRequestQueueConfiguration;
    }
}
