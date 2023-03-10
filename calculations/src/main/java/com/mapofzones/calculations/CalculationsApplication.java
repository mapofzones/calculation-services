package com.mapofzones.calculations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CalculationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculationsApplication.class, args);
    }

}
