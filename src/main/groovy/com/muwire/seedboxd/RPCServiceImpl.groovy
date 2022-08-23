package com.muwire.seedboxd

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.muwire.core.InfoHash
import com.muwire.core.SharedFile
import com.muwire.core.files.DirectoryUnsharedEvent
import com.muwire.core.files.FileSharedEvent
import com.muwire.core.files.FileUnsharedEvent
import com.muwire.core.files.directories.Visibility
import com.muwire.core.files.directories.WatchedDirectory
import com.muwire.core.files.directories.WatchedDirectoryConfigurationEvent
import net.i2p.data.Base64
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component


@Component
class RPCServiceImpl implements RPCService {
    
    @Autowired CoreService coreService
    @Autowired ConfigurableApplicationContext ctx
    
    SeedboxStatus status() {
        coreService.getStatus()
    }
    
    boolean share(String path) {
        File file = new File(path)
        if (!file.exists() || !file.canRead())
            return false
     
        def event = new FileSharedEvent(file: file, fromWatch: false)
        coreService.getCore().getEventBus().publish(event)
        true
    }
    
    void configDir(String path, boolean subdirs, boolean auto, int interval) {
        File file = new File(path)
        def event = new WatchedDirectoryConfigurationEvent(directory: file,
            subfolders: subdirs,
            autoWatch: auto,
            syncInterval: interval,
            visibility: Visibility.EVERYONE)
        coreService.getCore().getEventBus().publish(event)
    }
    
    FolderConfiguration getConfig(String path) {
        File file = new File(path)
        WatchedDirectory wd = coreService.getCore().getWatchedDirectoryManager().getDirectory(file)
        if (wd == null)
            return null
        FolderConfiguration rv = new FolderConfiguration()
        rv.auto = wd.autoWatch
        rv.syncInterval = wd.syncInterval
        rv
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
            if (!coreService.getCore().getFileManager().getPositiveTree().contains(file))
                return 0
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
        LoggerFactory.getILoggerFactory().getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.OFF)
        Thread.setDefaultUncaughtExceptionHandler({t,e -> } as Thread.UncaughtExceptionHandler)
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
