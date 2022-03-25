package com.gek.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static  Runnable runnable=new Runnable() {
        @Override
        public void run() {
//            1.创建连接工厂
            ConnectionFactory connectionFactory =new ConnectionFactory();
            connectionFactory.setHost("192.168.140.174");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/");
            connectionFactory.setPassword("admin");
            connectionFactory.setUsername("admin");
//            获取队列的名称
            final String queueName=Thread.currentThread().getName();
            Connection connection=null;
            Channel channel=null;
            try {
//                    从连接工厂获取连接
                    connection = connectionFactory.newConnection();
//                    从连接中获取通道
                    channel=connection.createChannel();
            channel.basicConsume(queueName, true, new DeliverCallback() {
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
//        启动三个线程去执行
        new Thread(runnable,"queue1").start();
        new Thread(runnable,"queue2").start();
        new Thread(runnable,"queue3").start();
    }
}
