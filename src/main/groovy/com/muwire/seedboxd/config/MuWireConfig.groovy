package com.muwire.seedboxd.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

@ConstructorBinding
@ConfigurationProperties("muwire")
@Validated
class MuWireConfig {
    @MuWireNickname
    final String nickname
    
    MuWireConfig(String nickname) {
        this.nickname = nickname
    }
}
