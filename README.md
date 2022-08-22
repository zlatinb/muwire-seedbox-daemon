The GitHub repo is a read-only mirror of the [I2P GitLab](https://i2pgit.org/zlatinb/muwire-seedbox-daemon) repo.

# MuWire Seedbox Daemon

A headless MuWire daemon that only shares files and is controlled via JSON-RPC

Status: Planning

## Reasoning

There has been some demand for a "Seedbox"-type solutions where a headless daemon runs and only serves files.  Such daemon would not offer any search, download or messaging functionality.

## API

The daemon would expose a JSON-RPC endpoint that supports the following actions:

* **share** - shares a directory or file on the local filesystem
* **unsharePath** - unshares a directory or file on the local filesystem
* **unshareHash** - unshares any files with that hash
* **filter** - searches the *local library* for a regex and returns matching shared files
* **change_config** - changes the named configuration setting(s) to a new value
* **hash_stats** - provided the hash of a file, returns verbose statistics about files matching that hash
* **status** - returns metrics about the daemon itself such as uptime, connection status, etc.
* **reload** - reloads the MuWire core without restarting the daemon process
* **shutdown** - gracefully shuts down the daemon

## Integration with console

The daemon is intended to be integrated with a seedbox console which manages one or more such daemons.

## Building

The MuWIre core is not published to MavenCentral so it needs to be build locally.  You only need to perform this step once.
1. Clone the `muwire` project
2. Execute `./gradlew core:publish` in that project
3. Back to this project, execute `./gradlew assemble`
3. An executable .jar file will be created in `build/libs`

## Configuration
Create a file called `application.properties` in the same directory as the .jar file.

The following configuration options are supported

|Name|Description|Required|
|---|---|---|
|i2p.host|Host where the I2P or I2Pd router is running|yes|
|i2p.port|Port on which the I2CP interface is listening|yes|
|i2p.tunnelLength|How long should the tunnels be |yes|
|i2p.tunnelQuantity|How many tunnels to build|yes|
|i2p.tunnelName|Name of the tunnel to report to the I2P router | No|
|muwire.nickname|NIckname to use on the MuWire network|Yes|
|muwire.workDir|Directory to use for storing file indices and others|Yes|
|rpc.iface|Interface on which to bind the JSON-RPC endpoint|Yes|
|rpc.port|Port on which to bind the JSON RPC endpoint|Yes|

Optionally, you can enable `Actuator` endpoints for many metrics by adding the following property:
```
management.endpoints.web.exposure.include=*
```
Go to `/actuator` to asee all available endpoints.  MuWire-specific metrics are available at `/actuator/muwire`


## Running
The jar built in the previous step is executable on Linux systems.  You can integrate it with systemd or system V if you like.  The `application.properties` file needs to be in the same directory as the jar file when it's launched.

If everything goes well, the Spring Boot logo will be printed to stdout.

## Try some commands

Assuming that you bound the rpc interface to localhost:12345

To check the current status of the seedbox daemon:
```
curl  -d '{"id":0,"method":"status"}' http://localhost:12345/seedbox
```

To share a file or directory on the local filesystem:
```
curl  -d '{"id":0,"method":"share", "params":["/absolute/path"]}' http://localhost:12345/seedbox
```

To un-share a file or directory that is currently shared:
```
curl  -d '{"id":0,"method":"unsharePath", "params":["/absolute/path"]}' http://localhost:12345/seedbox
```

To shut down the daemon gracefully:
```
curl  -d '{"id":0,"method":"shutdown"}' http://localhost:12345/seedbox
```

