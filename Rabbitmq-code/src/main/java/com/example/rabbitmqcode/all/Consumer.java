package com.example.rabbitmqcode.all;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static Runnable runnable=new Runnable() {
        @Override
        public void run() {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.140.174");
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setVirtualHost("/");
            Connection connection=null;
            Channel channel=null;
            try {
                 connection = factory.newConnection();
                 channel = connection.createChannel();
                 String queueName=Thread.currentThread().getName();
                 DeliverCallback deliverCallback =new DeliverCallback() {
                    @Override
                    public void handle(String consumerTag, Delivery message) throws IOException {
                        System.out.println(queueName+"收到的消息是:"+new String(message.getBody(),"UTF-8"));
                    }
                };
                CancelCallback cancelCallback = new CancelCallback() {
                    @Override
                    public void handle(String s) throws IOException {
                        System.out.println("接收消息失败");
                    }
                };
                channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
                System.out.println(queueName+"开始接收消息");
                System.in.read();
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
    };

    public static void main(String[] args) {
        new Thread(runnable,"queue5").start();
        new Thread(runnable,"queue6").start();
        new Thread(runnable,"queue7").start();
    }
}
