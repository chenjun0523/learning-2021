package com.hunter.activemq03.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;

@Service
public class SenderService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendStringQueue(String destination, String msg) {
        System.out.println("send...");
        ActiveMQQueue queue = new ActiveMQQueue(destination);
        jmsMessagingTemplate.afterPropertiesSet();

        ConnectionFactory factory = jmsMessagingTemplate.getConnectionFactory();

        try {
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue2 = session.createQueue(destination);

            MessageProducer producer = session.createProducer(queue2);

            TextMessage message = session.createTextMessage("hahaha");

            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }

        jmsMessagingTemplate.convertAndSend(queue, msg);
    }

    public void sendStringQueueList(String destination, String msg) {
        System.out.println("xxooq");
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(destination), list);
    }

}
