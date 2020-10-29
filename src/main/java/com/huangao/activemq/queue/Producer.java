package com.huangao.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author :huangao
 */
public class Producer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.127.201:61616";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话(第一个参数：事务 第二个参数：签收)
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(队列/主题)
        Queue queue = session.createQueue("myQueue");
        //5.创建消息生产者
         MessageProducer messageProducer = session.createProducer(queue);
        //6.发送消息
        for(int i=0;i<3;i++){
            TextMessage textMessage = session.createTextMessage("message----"+i);
            messageProducer.send(textMessage);
        }
        //7.关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送完毕。。。。。。");
    }
}
