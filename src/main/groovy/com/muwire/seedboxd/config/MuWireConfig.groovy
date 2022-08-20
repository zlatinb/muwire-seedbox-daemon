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
    
    @WriteableDir
    final File workDir
    
    MuWireConfig(String nickname, File workDir) {
        this.nickname = nickname
        this.workDir = workDir
    }
}
