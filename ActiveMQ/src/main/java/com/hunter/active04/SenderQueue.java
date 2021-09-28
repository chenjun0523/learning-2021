package com.hunter.active04;

import org.apache.activemq.ActiveMQConnectionFactory;

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
        producer.setTimeToLive(1000);
        Person person = new Person("hunter", 21, 1);
        ObjectMessage objectMessage = session.createObjectMessage(person);
        producer.send(objectMessage);

        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("name", "Hunter");
        producer.send(mapMessage);

        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.writeBoolean(true);
        bytesMessage.writeUTF("hi~");
        producer.send(bytesMessage);




        // 6. 关闭连接

        session.close();
        System.out.println("System exit...");
        System.out.println();
    }
}
