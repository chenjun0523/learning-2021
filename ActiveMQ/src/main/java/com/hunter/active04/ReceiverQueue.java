package com.hunter.active04;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.Arrays;

public class ReceiverQueue {
    public static void main(String[] args) throws JMSException {

        // 1. 获取连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
        // activeMQConnectionFactory.setTrustAllPackages(true);
        System.out.println(Person.class.getPackage().getName());
        // activeMQConnectionFactory.setTrustedPackages(Arrays.asList(Person.class.getPackage().getName()));
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3. 获取session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Destination queue = session.createQueue("user");

        // 5. 消息消费者
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        Serializable object = objectMessage.getObject();
                        Person person = (Person) object;
                        System.out.println(person.toString());
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
