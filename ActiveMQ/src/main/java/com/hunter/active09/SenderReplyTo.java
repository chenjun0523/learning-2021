package com.hunter.active09;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

public class SenderReplyTo {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 1. 获取连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3. 获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Destination queue = session.createQueue("user");
        Destination xxoo = session.createQueue("xxoo");

        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("hahaha" + i);
            textMessage.setJMSReplyTo(xxoo);
            producer.send(textMessage);
        }

        MessageConsumer consumer = session.createConsumer(xxoo);
        consumer.setMessageListener(message -> {
            try {
                System.out.println(Thread.currentThread().getName() + "-->" + ((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });


        // 6. 关闭连接
        System.out.println(Thread.currentThread().getName() + "-->" + "System exit...");

        Map<String, String> map = new HashMap<>();
        map.put("a", "97");
        map.putIfAbsent("a", "97");
    }
}
