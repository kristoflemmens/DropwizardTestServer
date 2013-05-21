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

    public static AutomatedRequestQueue create(AutomatedRequestQueueConfiguration configuration) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(configuration.getBrokerURL());
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(configuration.isTransacted(), configuration.getAcknowledgeMode());
        return new AutomatedRequestQueue(session, session.createProducer(session.createQueue(configuration.getQueueName())));
    }

    public void publish(String automatedRequest) throws JMSException {
          producer.send(session.createTextMessage(automatedRequest));
    }
}
