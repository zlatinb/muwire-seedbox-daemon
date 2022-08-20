package com.muwire.seedboxd

import com.muwire.seedboxd.config.RPCConfig
import org.springframework.boot.web.server.ConfigurableWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class WebServerConfigurator implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{
    private final RPCConfig rpcConfig
    
    WebServerConfigurator(RPCConfig rpcConfig) {
        this.rpcConfig = rpcConfig
    }

    @Override
    void customize(ConfigurableWebServerFactory factory) {
        factory.setAddress(rpcConfig.getIface())
        factory.setPort(rpcConfig.getPort())
    }
}
