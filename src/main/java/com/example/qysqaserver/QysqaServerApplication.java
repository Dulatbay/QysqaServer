package com.example.qysqaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QysqaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QysqaServerApplication.class, args);
    }

}
