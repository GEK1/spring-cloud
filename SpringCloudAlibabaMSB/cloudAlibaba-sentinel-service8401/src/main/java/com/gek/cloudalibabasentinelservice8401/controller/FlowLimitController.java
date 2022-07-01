package com.gek.cloudalibabasentinelservice8401.controller;

import com.gek.cloudalibabasentinelservice8401.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class FlowLimitController {
    @Autowired
    private TestService testService;

    @GetMapping("/testA")
    public String testA(){
        log.info(Thread.currentThread().getName()+"testA");
        return testService.common();
    }

    @GetMapping("/testB")
    public String testB(){
        return testService.common();
    }
    @GetMapping("/testC")
    public String testC(){
        try {
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "---------testC";
    }
}
