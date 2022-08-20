package com.muwire.seedboxd;

public interface RPCService {
    SeedboxStatus status();
    boolean share(String path);
    boolean shutdown();
}
