package com.muwire.seedboxd;

public interface RPCService {
    SeedboxStatus status();
    boolean share(String path);
    int unsharePath(String path);
    int unshareHash(String hash);
    boolean shutdown();
}
