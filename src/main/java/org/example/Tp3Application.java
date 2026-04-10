package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Tp3Application {
    public static void main(String[] args) {
        SpringApplication.run(Tp3Application.class, args);
    }
}