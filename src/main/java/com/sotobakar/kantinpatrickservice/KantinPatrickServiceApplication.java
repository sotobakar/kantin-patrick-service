package com.sotobakar.kantinpatrickservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KantinPatrickServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KantinPatrickServiceApplication.class, args);
    }

}
