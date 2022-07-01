package com.gek.cloudalibabaconsumernacos8083.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @GetMapping(value = "/consumer/nacos")
    public String getDiscovery(){
        return  restTemplate.getForObject(serverURL+"/gek",String.class);
    }
}
