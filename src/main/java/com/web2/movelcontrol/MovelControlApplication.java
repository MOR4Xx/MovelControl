package com.web2.movelcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MovelControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovelControlApplication.class, args);
    }

}
