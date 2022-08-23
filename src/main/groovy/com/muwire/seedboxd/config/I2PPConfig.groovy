package com.muwire.seedboxd.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ConfigurationProperties("i2p")
@Validated
class I2PConfig {
    
    @NotNull
    InetAddress host
    
    @Min(value = 1024L, message = "Port cannot be less than 1024")
    @Max(value = 65535L, message = "Port out of range")
    int port
    
    @Min(value = 1L, message = "Tunnel length zero is a bad idea")
    @Max(value = 3L, message = "Too long tunnel")
    int tunnelLength = 3
    
    @Min(value = 1L, message = "You need at least one tunnel")
    @Max(value = 6L, message = "Too many tunnels")
    int tunnelQuantity = 4
    
    String tunnelName = "MuWire Seedbox"
}
