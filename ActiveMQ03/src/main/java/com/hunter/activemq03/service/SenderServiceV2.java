package com.hunter.activemq03.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;

@Service
public class SenderServiceV2 {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String msg) {
        System.out.println("send...");

/*        try {
            ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }*/
        jmsTemplate.send(destination, session -> {

            TextMessage textMessage = session.createTextMessage("xxoo");
            textMessage.setStringProperty("hehe", "enen");
            return textMessage;
        });
    }

    public void send2(String destination, String msg) {


        ArrayList<String> list = new ArrayList<>();

        list.add("malaoshi");
        list.add("lain");
        list.add("zhou");
        jmsMessagingTemplate.convertAndSend(destination, list);
    }

    public void send3(String destination, String msg) {


        ArrayList<String> list = new ArrayList<>();

        list.add("malaoshi");
        list.add("lain");
        list.add("zhou");
        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(destination), list);
    }

}
