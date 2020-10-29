package com.huangao.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author :huangao
 */
public class Consumer {

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
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //6.消费消息
        while(true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if(textMessage!=null){
                System.out.println("消费者接收到消息："+textMessage.getText());
            }else{
                break;
            }
        }
        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
