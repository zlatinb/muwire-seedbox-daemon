package com.muwire.seedboxd.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.Min

@ConfigurationProperties("muwire")
@Validated
class MuWireConfig {
    @MuWireNickname
    String nickname
    
    @WriteableDir
    File workDir
    
    boolean allowBrowsing = true
    boolean allowRegexQueries = true
    int totalUploadSlots = -1
    int uploadSlotsPerUser = -1
    @Min(value = 1L, message = "You need at least one CPU core for hashing")
    int hashingCores = Math.max(1,(int)(Runtime.getRuntime().availableProcessors() / 4))
    boolean throttleLoadingFiles = false
    boolean enableFeed = false
    boolean showPaths = true
}
