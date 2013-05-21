package com.example.dropwizardtestserver;

import com.example.dropwizardtestserver.core.AutomatedRequestQueueConfiguration;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.testing.junit.DropwizardServiceRule;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.jms.*;

import static org.fest.assertions.api.Assertions.assertThat;

public class TestServiceIntegrationTest {

    private static final String AUTOMATED_REQUEST = "<xml></xml>";

    @ClassRule
    public static DropwizardServiceRule<TestConfiguration> testServer = new DropwizardServiceRule<>(TestService.class, "test.yml");

    private Client client;
    private MessageConsumer consumer;
    private Session session;
    private Connection connection;

    @Before
    public void setup() throws Exception {
        client = new JerseyClientBuilder()
                .using(new JerseyClientConfiguration())
                .using(testServer.getEnvironment())
                .build();

        AutomatedRequestQueueConfiguration automatedRequestQueueConfiguration = testServer.getConfiguration().getAutomatedRequestQueueConfiguration();

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(automatedRequestQueueConfiguration.getBrokerURL());
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(automatedRequestQueueConfiguration.isTransacted(), automatedRequestQueueConfiguration.getAcknowledgeMode());
        Destination destination = session.createQueue(automatedRequestQueueConfiguration.getQueueName());
        consumer = session.createConsumer(destination);
    }

    @After
    public void tearDown() throws Exception {
        consumer.close();
        session.close();
        connection.stop();
        connection.close();
    }

    @Test
    public void simpleTest() throws Exception {
        client.resource("http://localhost:8080/request/automated").post(AUTOMATED_REQUEST);

        Message message = consumer.receiveNoWait();
        assertThat(message).isNotNull().isInstanceOf(TextMessage.class);
        assertThat(((TextMessage) message).getText()).isEqualTo(AUTOMATED_REQUEST);
    }

}
