package com.muwire.seedboxd

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan
class SeedboxD {
    public static void main(String [] args) {
        SpringApplication.run(SeedboxD.class, args)
    }
}
