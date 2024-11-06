package com.example.crypto_trading.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldService {
    
    private Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

    @GetMapping("/hello-world")
    public String helloWorld() {
        String message = "Hello World!";
        logger.info(message);
        return message;
    }
}
