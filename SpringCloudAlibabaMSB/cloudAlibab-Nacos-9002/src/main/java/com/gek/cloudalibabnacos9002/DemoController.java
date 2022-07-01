package com.gek.cloudalibabnacos9002;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/gek")
    public String getServerPort(){
        return  "Hello Nacos Discover"+serverPort;
    }
}
