package com.gek.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer{
//    private final static  String QUEUE_NAME="queue1";
    private final static  String EXCHANGE_NAME="fanout-exchange";
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
//            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
//            通过创建交换机，声明队列，绑定关系，路由key,发送消息和接收消息
//            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //准备消息内容
            String message="Hello RabbitMQ";
//            准备交换机
//            String exchangName="fanout-exchange";
//            定义路由key
            String routeKey="";
//            指定交换机类型
            String type="fanout";
            //发送消息给队列
            channel.basicPublish(EXCHANGE_NAME,routeKey,null,message.getBytes("UTF-8"));
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
