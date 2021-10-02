package com.hunter.active07;

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
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("hello schedule");
        long delay = 10 * 1000;
        long period = 2 * 1000;
        int repeat = 9;
        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
        textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);

        producer.send(textMessage);

        // 6. 关闭连接

        session.close();
        System.out.println("System exit...");
        System.out.println();
    }
}
