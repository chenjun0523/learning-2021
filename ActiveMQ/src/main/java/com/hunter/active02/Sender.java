package com.hunter.active02;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 1. 获取连接工厂
        /*ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );*/
        /**
         *  ActiveMQConnectionFactory.DEFAULT_USER 默认值是Null
         *  在activemq.xml配置文件里配置插件，设置用户名密码。
         * 		<plugins>
         * 			<simpleAuthenticationPlugin>
         * 				<users>
         * 					<authenticationUser username="admin" password="admin" groups="admins,publishers,consumers"/>
         * 					<authenticationUser username="publisher" password="publisher" groups="publishers,consumers"/>
         * 					<authenticationUser username="consumer" password="consumer" groups="consumers"/>
         * 					<authenticationUser username="guest" password="guest" groups="guests"/>
         * 				</users>
         * 			</simpleAuthenticationPlugin>
         * 		</plugins>
         */
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
        // 2. 获取一个向ActiveMQ的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 3. 获取session
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination， 消费端，也会从这个目的地取消息

        Queue queue = session.createQueue("user");

        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);
        // consumer -> 消费者
        // producer -> 创建者
        // 5.2 创建消息
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("hi + " + i);
            // 5.3 向目的地写入消息
            producer.send(textMessage);
            System.out.println(textMessage);
            session.commit();
        }
        // 6. 关闭连接

        session.close();
        System.out.println("System exit...");
        System.out.println();
    }
}
