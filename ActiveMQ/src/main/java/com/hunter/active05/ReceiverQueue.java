package com.hunter.active05;

import com.hunter.active04.Person;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class ReceiverQueue {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 1. 获取连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
        activeMQConnectionFactory.setTrustAllPackages(true);
        // activeMQConnectionFactory.setTrustedPackages(Arrays.asList(Person.class.getPackage().getName()));
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3. 获取session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        // 独占消费的消费者断开后，其他消费者再争抢消息
        // Destination queue = session.createQueue("user");
        // Destination queue = session.createQueue("user?consumer.exclusive=trueconsumer.priority=9");
        Destination queue = session.createQueue("xxooQ.user?consumer.exclusive=true&consumer.priority=10");
        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            Message receive = consumer.receive();
            TextMessage textMessage = (TextMessage) receive;
            // System.out.println("consumer2-exclusive:" + textMessage.getText());
            // System.out.println("consumer1-non-exclusive:" + textMessage.getText());

            System.out.println("consumer1-exclusive-10:" + textMessage.getText());
            // System.out.println("consumer2-exclusive-9:" + textMessage.getText());
            textMessage.acknowledge();
            Thread.sleep(2000);
        }
    }
}
