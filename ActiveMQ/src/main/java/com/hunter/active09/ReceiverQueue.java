package com.hunter.active09;

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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息
        Destination queue = session.createQueue("user");
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    System.out.println(textMessage.getJMSReplyTo());
                    MessageProducer producer = session.createProducer(textMessage.getJMSReplyTo());
                    TextMessage textMessage1 = session.createTextMessage("ok----");
                    producer.send(textMessage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
