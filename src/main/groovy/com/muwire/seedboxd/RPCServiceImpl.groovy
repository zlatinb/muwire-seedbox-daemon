package com.muwire.seedboxd

import com.muwire.core.Core
import com.muwire.core.InfoHash
import com.muwire.core.SharedFile
import com.muwire.core.files.DirectoryUnsharedEvent
import com.muwire.core.files.FileSharedEvent
import com.muwire.core.files.FileUnsharedEvent
import net.i2p.data.Base64
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
        rv.muwireName = coreService.getCore().getMe().getHumanReadableName()
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
    
    int unshareHash(String hash) {
        byte[] decoded = Base64.decode(hash)
        InfoHash infoHash = new InfoHash(decoded)
        SharedFile[] sf = coreService.getCore().getFileManager().rootToFiles.get(infoHash)
        if (sf == null)
            return 0
        def event = new FileUnsharedEvent(unsharedFiles: sf, deleted: false, implicit: false)
        coreService.getCore().getEventBus().publish(event)
        return sf.length
    }
    
    int unsharePath(String path) {
        File file = new File(path)
        if (file.isFile()) {
            SharedFile sf = coreService.getCore().getFileManager().fileToSharedFile.get(file)
            if (sf == null)
                return 0
            def event = new FileUnsharedEvent(unsharedFiles: new SharedFile[]{sf},
                deleted: false, implicit: false)
            coreService.getCore().getEventBus().publish(event)
            return 1
        } else if (file.isDirectory()) {
            def cb = new MiniFileUnsharedCallback()
            coreService.getCore().getEventBus().register(FileUnsharedEvent.class, cb)
            
            def event = new DirectoryUnsharedEvent(directories: new File[]{file}, deleted: false)
            coreService.getCore().getEventBus().publish(event)
            
            int rv = cb.await()
            coreService.getCore().getEventBus().unregister(FileUnsharedEvent.class, cb)
            return rv
        }
        return -1
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
    
    
    private static class MiniFileUnsharedCallback {
        int unshared = -1
        synchronized void onFileUnsharedEvent(FileUnsharedEvent event) {
            unshared = event.unsharedFiles.length
            notify()
        }
        
        synchronized int await() {
            while(unshared == -1)
                wait()
            unshared
        }
    }
}
