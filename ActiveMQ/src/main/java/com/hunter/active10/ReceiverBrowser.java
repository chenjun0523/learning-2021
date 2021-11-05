package com.hunter.active10;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

public class ReceiverBrowser {
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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Queue queue = session.createQueue("user");

        // 5. 消息browser
        QueueBrowser browser = session.createBrowser(queue);

        Enumeration enumeration = browser.getEnumeration();

        while (enumeration.hasMoreElements()) {
            TextMessage textMessage = (TextMessage) enumeration.nextElement();

            System.out.println("textMessage:" + textMessage.getText());
        }

        // 6. 获取消息

    }
}
