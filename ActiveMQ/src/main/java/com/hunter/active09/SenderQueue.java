package com.hunter.active09;

import org.apache.activemq.ActiveMQConnectionFactory;
import sun.security.krb5.internal.crypto.Des;

import javax.jms.*;

public class SenderQueue {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 1. 获取连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "vm://localhost?marshal=false&broker.persistent=false"
        );
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3. 获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Destination queue = session.createQueue("user");

        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("hahaha" + i);
            producer.send(textMessage);
        }

        MessageConsumer consumer = session.createConsumer(queue);

        Thread thread = new Thread(() -> {
            try {
                consumer.setMessageListener(message -> {
                    TextMessage message1 = (TextMessage) message;
                    try {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(message1.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                });
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "---" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread2.start();



        // 6. 关闭连接
    }
}
