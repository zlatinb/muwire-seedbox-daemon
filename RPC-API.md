# JSON-RPC API

This document is accurate as of version 0.0.9 of the daemon.

### *status*
Queries the current status of the daemon
Parameters: none
Returns: a structure containing various metrics about the daemon

### *share*
Shares a file or a folder on the local filesystem
Parameter1: /absolute/path/to/share  (can be file or a folder)
Returns: "false" if the path doesn't exist or cannot be read

### *unsharePath*
Unshares the provided absolute path, which can be pointing to a file or to a folder.
Parameter1: /absolute/path/to/unshare
Returns: number of unshared files

### *unshareHash*
Unshares all files that match the provided hash
Parameter1: base-64 encoded MuWire hash
Returns: number of unshared files 

### *configDir*
Configures sync parameters of a folder, optionally its subfolders.  This is useful when working with network mounts where the Linux "iNotify" facility doesn't work.
Parameter1: /absolute/path/to/configure
Parameter2: boolean (whether to apply to subfolders as well)
Parameter3: boolean (whether to use iNotify
Parameter4: int (frequency in seconds to perform manual sync)
Returns: false if the directory isn't shared, otherwise true

If Parameter3 is true, Parameter4 is ignored.

### *getConfig*
Returns the current sync configuration of the specified folder
Parameter1: /absolute/path/to/query
Returns: The current configuration or null if the directory isn't shared.

### *syncNow*
Forces a sync of the specified folder, optionally subfolders.
Parameter1: /absolute/path/to/sync
Parameter2: boolean (whether to apply to sub-folders)
Returns: Number of folders that will be synced

### *shutdown*
Performs a graceful shutdown of the daemon
Parameters: none
Returns: always true
