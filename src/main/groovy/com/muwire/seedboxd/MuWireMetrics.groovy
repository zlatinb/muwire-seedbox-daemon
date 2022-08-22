package com.muwire.seedboxd

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "muwire")
class MuWireMetrics {
    
    @Autowired private final CoreService coreService
    
    @ReadOperation
    SeedboxStatus muwire() {
        coreService.getStatus()
    }
}
