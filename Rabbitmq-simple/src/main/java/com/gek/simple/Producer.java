package com.gek.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer{
    private final static  String QUEUE_NAME="queue1";
    public static void main(String[] args) {
//        创建连接工程
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.140.174");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection =null;
        Channel channel =null;
        try {
//            创建连接connection
            connection = factory.newConnection();
//            通过连接获取通道
             channel = connection.createChannel();
//            通过创建交换机，声明队列，绑定关系，路由key,发送消息和接收消息
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //准备消息内容
            String message="Hello RabbitMQ";
            //发送消息给队列
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(channel!=null && channel.isOpen()){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null && connection.isOpen()){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
