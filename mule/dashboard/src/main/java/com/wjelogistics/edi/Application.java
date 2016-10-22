package com.wjelogistics.edi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration()
public class Application {
    @RequestMapping("/")
    String home() {
        return "Hello from Spring Boot!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
