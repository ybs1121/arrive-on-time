package com.arriveontime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ArriveOnTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArriveOnTimeApplication.class, args);
    }
}
