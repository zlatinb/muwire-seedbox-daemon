package com.muwire.seedboxd;

public interface RPCService {
    SeedboxStatus status();
    boolean share(String path);
    boolean configDir(String path, boolean subdirs, boolean auto, int interval);
    FolderConfiguration getConfig(String path);
    int syncNow(String path, boolean subdirs);
    int unsharePath(String path);
    int unshareHash(String hash);
    int exportConnections(String path);
    int importConnections(String path);
    boolean shutdown();
}
