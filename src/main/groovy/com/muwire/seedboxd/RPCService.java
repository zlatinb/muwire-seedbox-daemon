package com.muwire.seedboxd;

public interface RPCService {
    SeedboxStatus status();
    boolean share(String path);
    void configDir(String path, boolean subdirs, boolean auto, int interval);
    FolderConfiguration getConfig(String path);
    int unsharePath(String path);
    int unshareHash(String hash);
    boolean shutdown();
}
