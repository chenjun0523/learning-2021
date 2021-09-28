package com.hunter.active04;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class ReceiverQueue {
    public static void main(String[] args) throws JMSException {

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

        Destination queue = session.createQueue("user");
        Destination queue2 = session.createQueue("xxooQ.user");
        MessageConsumer consumer2 = session.createConsumer(queue2);
        consumer2.setMessageListener((message) -> {
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                try {
                    Serializable object = objectMessage.getObject();
                    Person person = (Person) object;
                    System.out.println("ObjectMessage: " + person.toString());
                    objectMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            } else {
                if (message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println("MapMessage: " + mapMessage.getString("name"));
                        mapMessage.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
                if (message instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) message;
                    try {
                            /*System.out.println("BytesMessage: " + bytesMessage.readBoolean());
                            System.out.println("BytesMessage: " + bytesMessage.readUTF());*/

                        FileOutputStream fileOutputStream = new FileOutputStream("D://activemq.txt");
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = bytesMessage.readBytes(bytes)) != -1) {
                            fileOutputStream.write(bytes, 0 , len);
                        }
                        bytesMessage.acknowledge();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        // 5. 消息消费者
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        Serializable object = objectMessage.getObject();
                        Person person = (Person) object;
                        System.out.println("ObjectMessage: " + person.toString());
                        objectMessage.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (message instanceof MapMessage) {
                        MapMessage mapMessage = (MapMessage) message;
                        try {
                            System.out.println("MapMessage: " + mapMessage.getString("name"));
                            mapMessage.acknowledge();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                    if (message instanceof BytesMessage) {
                        BytesMessage bytesMessage = (BytesMessage) message;
                        try {
                            /*System.out.println("BytesMessage: " + bytesMessage.readBoolean());
                            System.out.println("BytesMessage: " + bytesMessage.readUTF());*/

                            FileOutputStream fileOutputStream = new FileOutputStream("D://activemq.txt");
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            while ((len = bytesMessage.readBytes(bytes)) != -1) {
                                fileOutputStream.write(bytes, 0 , len);
                            }
                            bytesMessage.acknowledge();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }
}
