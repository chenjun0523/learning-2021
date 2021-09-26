package com.hunter.active03;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
    public static void main(String[] args) throws JMSException {

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
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Destination queue = session.createQueue("user");

        // 5. 消息消费者
        MessageConsumer consumer = session.createConsumer(queue);

        // 6. 获取消息
        while (true) {
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println("message:" + message.getText());
            message.acknowledge();
        }
    }
}
