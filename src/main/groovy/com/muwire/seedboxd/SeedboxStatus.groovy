package com.muwire.seedboxd

class SeedboxStatus {
    String muwireName
    volatile boolean i2pRouterConnected
    volatile boolean allFilesLoaded
    int sharedFiles
    int connectionsIn
    int connectionsOut
    int speedOut
    volatile int totalUploadRequests
    int uploadsInProgress
    volatile long totalUploadedBytes
    
    int hostsKnown
    int hostsFailing
    int hostsHopeless
    
    volatile int totalSearches
    volatile int totalResponses
}
