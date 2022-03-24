package com.gek.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
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
            channel.basicConsume(QUEUE_NAME, true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery message) throws IOException {
                    System.out.println("收到消息是:" + new String(message.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接收消息失败");
                }
            });
            System.out.println("开始接收消息");
            System.in.read();
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
