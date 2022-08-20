package com.muwire.seedboxd

import com.muwire.seedboxd.config.I2PConfig
import org.springframework.stereotype.Service

@Service
class CoreService {
    private final I2PConfig i2PProperties
    
    CoreService(I2PConfig i2PProperties) {
        this.i2PProperties = i2PProperties
        println i2PProperties.getHost()
    }
}
