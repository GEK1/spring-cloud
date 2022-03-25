package com.example.rabbitmqcode.all;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producter {
    private  final static String EXCHANGE_NAME="direct_message_exchange";
    public static void main(String[] args) {
        //1.建立工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.140.174");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection=null;
        Channel channel=null;
        //2.工厂建立连接
        try {
             connection = factory.newConnection();
            //3.连接建立通道
             channel = connection.createChannel();
             String exchange_type="direct";
            //4.通道声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME,exchange_type,true);
            //5.声明队列
            channel.queueDeclare("queue5",true,false,false,null);
            channel.queueDeclare("queue6",true,false,false,null);
            channel.queueDeclare("queue7",true,false,false,null);
            //6.绑定队列和交换机的消息
            channel.queueBind("queue5",EXCHANGE_NAME,"order");
            channel.queueBind("queue6",EXCHANGE_NAME,"order");
            channel.queueBind("queue7",EXCHANGE_NAME,"email");
            String message="Hello gek study rabbitmq";
            //7.发送消息
            channel.basicPublish(EXCHANGE_NAME,"order",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
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
