package com.hunter.active04;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiverTopic {
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

        Destination topic = session.createTopic("user");

        // 5. 消息消费者
        MessageConsumer consumer = session.createConsumer(topic);

        // 6. 获取消息
        // 同步， 阻塞
/*        while (true) {
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println("message:" + message.getText());
            message.acknowledge();
        }*/
        // 异步， 监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("message:" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
