package com.hunter.activemq03.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {

    @JmsListener(destination = "user", containerFactory = "jmsListenerContainerQueue") public void receiveStringQueue(String msg) {
        System.out.println("The message received is " + msg);
    }

    @JmsListener(destination = "ooo", containerFactory = "jmsListenerContainerTopic") public void receiveStringTopic(String msg) {
        System.out.println("The message received is " + msg);
    }

}
