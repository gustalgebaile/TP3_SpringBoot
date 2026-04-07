package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Habilita o uso de cache na aplicação
public class Tp3Application {
    public static void main(String[] args) {
        SpringApplication.run(Tp3Application.class, args);
    }
}