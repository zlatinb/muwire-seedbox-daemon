The GitHub repo is a read-only mirror of the [I2P GitLab](https://i2pgit.org/zlatinb/muwire-seedbox-daemon) repo.

# MuWire Seedbox Daemon

A headless MuWire daemon that only shares files and is controlled via JSON-RPC

Status: Planning

## Reasoning

There has been some demand for a "Seedbox"-type solutions where a headless daemon runs and only serves files.  Such daemon would not offer any search, download or messaging functionality.

## API

The daemon would expose a JSON-RPC endpoint that supports the following actions:

* **share** - shares a directory or file on the local filesystem
* **unshare** - unshares a directory or file on the local filesystem
* **filter** - searches the *local library* for a regex and returns matching shared files
* **change_config** - changes the named configuration setting(s) to a new value
* **hash_stats** - provided the hash of a file, returns verbose statistics about files matching that hash
* **status** - returns metrics about the daemon itself such as uptime, connection status, etc.
* **reload** - reloads the MuWire core without restarting the daemon process
* **shutdown** - gracefully shuts down the daemon

## Integration with console

The daemon is intended to be integrated with a seedbox console which manages one or more such daemons.

# Building

## One-time building of the MuWire core artifact

The MuWIre core is not published to MavenCentral so it needs to be build locally.  You only need to perform this step once:
1. Clone the `muwire` project
2. Execute `./gradlew core:publish` 

## Quick start
```
./gradlew bootRun
```


