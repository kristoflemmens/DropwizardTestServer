package com.example.dropwizardtestserver;

import com.example.dropwizardtestserver.resources.AutomatedRequestResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import static com.example.dropwizardtestserver.core.AutomatedRequestQueue.create;

public class TestService extends Service<TestConfiguration> {

    public static void main(String[] args) throws Exception {
        new TestService().run(args);
    }

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.setName("DropwizardTestServer");
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new AutomatedRequestResource(create(configuration.getAutomatedRequestQueueConfiguration())));
    }

}
