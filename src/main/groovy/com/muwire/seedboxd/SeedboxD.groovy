package com.muwire.seedboxd

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean

@SpringBootApplication
@ConfigurationPropertiesScan
class SeedboxD {
    public static void main(String [] args) {
        SpringApplication.run(SeedboxD.class, args)
    }
    
    @Bean(name = "/seedbox")
    JsonServiceExporter jsonServiceExporter(RPCService serviceImpl) {
        def rv = new JsonServiceExporter()
        rv.setServiceInterface(RPCService.class)
        rv.setService(serviceImpl)
        rv
    }
}
