package com.gek.cloudalibabnacos9002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabNacos9002Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabNacos9002Application.class, args);
    }

}
