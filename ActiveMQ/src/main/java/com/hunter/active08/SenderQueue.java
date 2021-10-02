package com.hunter.active08;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class SenderQueue {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 1. 获取连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 3. 获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Queue queue = session.createQueue("user");

        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);

        MapMessage mapMessage1 = session.createMapMessage();
        mapMessage1.setString("name", "hunter");
        mapMessage1.setInt("age", 18);
        mapMessage1.setDouble("height", 168.00);
        mapMessage1.setStringProperty("name", "hunter");
        mapMessage1.setIntProperty("age", 18);

        MapMessage mapMessage2 = session.createMapMessage();
        mapMessage2.setString("name", "vivian");
        mapMessage2.setInt("age", 25);
        mapMessage2.setDouble("height", 164.00);
        mapMessage2.setStringProperty("name", "vivian");
        mapMessage2.setIntProperty("age", 25);

        MapMessage mapMessage3 = session.createMapMessage();
        mapMessage3.setString("name", "rae");
        mapMessage3.setInt("age", 28);
        mapMessage3.setDouble("height", 165.00);
        mapMessage3.setStringProperty("name", "rae");
        mapMessage3.setIntProperty("age", 28);


        producer.send(mapMessage1);
        producer.send(mapMessage2);
        producer.send(mapMessage3);

        // 6. 关闭连接

        session.close();
        System.out.println("System exit...");
        System.out.println();
    }
}
