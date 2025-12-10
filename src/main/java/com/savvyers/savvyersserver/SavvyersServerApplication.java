package com.savvyers.savvyersserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SavvyersServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SavvyersServerApplication.class, args);
    }
}
