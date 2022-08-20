package com.muwire.seedboxd.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ConstructorBinding
@ConfigurationProperties("rpc")
@Validated
class RPCConfig {
    @NotNull
    final InetAddress iface

    @Min(value = 1024L, message = "Port cannot be less than 1024")
    @Max(value = 65535L, message = "Port out of range")
    final int port
    
    RPCConfig(InetAddress iface, int port) {
        this.iface = iface
        this.port = port
    }
    
}
