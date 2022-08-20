package com.muwire.seedboxd

import com.muwire.core.Core
import com.muwire.core.files.FileSharedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

@Component
class RPCServiceImpl implements RPCService {
    
    @Autowired CoreService coreService
    @Autowired ConfigurableApplicationContext ctx
    
    SeedboxStatus status() {
        SeedboxStatus rv = new SeedboxStatus()
        rv.allFilesLoaded = coreService.allFilesLoaded
        rv.i2pRouterConnected = coreService.i2pRouterConnected
        rv.sharedFiles = coreService.getCore().getFileManager().getFileToSharedFile().size()
        rv
    }
    
    boolean share(String path) {
        File file = new File(path)
        if (!file.exists() || !file.canRead())
            return false
     
        def event = new FileSharedEvent(file: file, fromWatch: false)
        coreService.getCore().getEventBus().publish(event)
        true
    }
    
    boolean shutdown() {
        coreService.getCore().shutdown()
        def shutdowner = {
            Thread.sleep(100)
            ctx.close()
            System.exit(0)
        } as Runnable
        Thread t = new Thread(shutdowner)
        t.start()
        true
    }
    
}
