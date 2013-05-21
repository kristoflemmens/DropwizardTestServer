package com.example.dropwizardtestserver.core;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AutomatedRequestQueue {
    private final Session session;
    private final MessageProducer producer;

    public AutomatedRequestQueue(Session session, MessageProducer producer) {
        this.session = session;
        this.producer = producer;
    }

    public static AutomatedRequestQueue create(AutomatedRequestQueueConfiguration automatedRequestQueueConfiguration) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(automatedRequestQueueConfiguration.getBrokerURL());
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(automatedRequestQueueConfiguration.isTransacted(), automatedRequestQueueConfiguration.getAcknowledgeMode());
        return new AutomatedRequestQueue(session, session.createProducer(session.createQueue(automatedRequestQueueConfiguration.getQueueName())));
    }

    public void publish(String automatedRequest) throws JMSException {
          producer.send(session.createTextMessage(automatedRequest));
    }
}
