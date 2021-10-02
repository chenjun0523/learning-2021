package com.hunter.active07;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

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
        Destination queue = session.createQueue("user");
        MessageConsumer consumer = session.createConsumer(queue);
        for (int i = 0; ; i++) {
            Message receive = consumer.receive();
            if (receive instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) receive;
                System.out.println(textMessage.getText());
                textMessage.acknowledge();
            }
        }
    }
}
