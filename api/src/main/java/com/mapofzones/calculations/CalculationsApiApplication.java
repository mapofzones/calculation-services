package com.mapofzones.calculations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CalculationsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculationsApiApplication.class, args);
    }

}
