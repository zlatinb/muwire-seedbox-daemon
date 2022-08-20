package com.muwire.seedboxd

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SeedboxD {
    public static void main(String [] args) {
        println "in main"
        SpringApplication.run(SeedboxD.class, args)
    }
}
