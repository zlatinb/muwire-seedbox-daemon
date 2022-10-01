The GitHub repo is a read-only mirror of the [I2P GitLab](https://i2pgit.org/zlatinb/muwire-seedbox-daemon) repo.

# MuWire Seedbox Daemon

A headless MuWire daemon that only shares files and is controlled via JSON-RPC

Status: Alpha

## Reasoning

There has been some demand for a "Seedbox"-type solutions where a headless daemon runs and only serves files.  Such daemon would not offer any search, download or messaging functionality.

## API

See the `RPC-API.md` file for the latest documentation of the JSON-RPC api.

## Integration with consoles and monitoring systems.

The daemon is intended to be integrated with a seedbox console which manages one or more such daemons.  It also exposes standard Spring Boot Actuator endpoint which can be integrated with various monitoring systems.  Integration with Zabbix has already been demonstrated and a template file is available in this source - `zbx_muwire-seedbox_template_v0.1.yaml`

## Building

### The MuWire Core artifact
The MuWIre core is not published to MavenCentral yet so it needs to be build locally.  You only need to perform this step once.

1. Clone the `muwire` project and go inside the `muwire` folder
2. `git checkout seedbox-core-0.1.0`
2. `./gradlew core:publish`

### The Seedbox Daemon
1. Back to this project, execute `./gradlew assemble`
2. An executable .jar file will be created in `build/libs`

## Configuration

### The `application.properties` file
Create a file called `application.properties` in the same directory as the .jar file.

The following configuration options are required

|Name|Description|
|---|---|
|i2p.host|Host where the I2P or I2Pd router is running|
|i2p.port|Port on which the I2CP interface is listening|
|muwire.nickname|NIckname to use on the MuWire network|
|muwire.workDir|Directory to use for storing file indices and others|
|rpc.iface|Interface on which to bind the JSON-RPC endpoint|
|rpc.port|Port on which to bind the JSON RPC endpoint|

The following configuration options are optional

|Name| Description                                       |Default value|
|---|---------------------------------------------------|---|
|i2p.tunnelLength| How long should the tunnels be                    |3|
|i2p.tunnelQuantity| How many tunnels to build                         |4|
|i2p.tunnelName| Name of the tunnel to report to the I2P router    |"MuWire Seedbox"|
|muwire.allowBrowsing| Whether to allow browsing of shared files         |true|
|muwire.allowRegexQueries| Whether to allow regular expression queries       |true|
|muwire.totalUploadSlots| How many uploads to allow simultaneously          |-1(unlimited)|
|muwire.uploadSlotsPerUser| How many simultaneous uploads to allow per user   |-1(unlimited)|
|muwire.hashingCores| How many CPU cores to use for hashing files       | Available cores / 4 |
|muwire.throttleLoadingFiles| Whether to throttle loading of library on startup |false|
|muwire.enableFeed|Whether to enable and auto-publish files to the feed| false|
|muwire.showPaths|Whether to show folder names in search results | true|


### The `Actuator` endpoints
Optionally, you can enable `Actuator` endpoints for many metrics by adding the following property:
```
management.endpoints.web.exposure.include=*
```
Go to `/actuator` to asee all available endpoints.  MuWire-specific metrics are available at `/actuator/muwire`

### Logging

There are [many standard ways](https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/reference/html/boot-features-logging.html)
to configure logging for Spring Boot applications.  If you just want to get going as quickly as you can, add the following line 
to `application.properties`:
```
logging.level.root = ERROR
```
## Running
The jar built in the first step is executable on Linux systems.  You can integrate it with systemd or system V if you like.  The `application.properties` file needs to be in the same directory as the jar file when it's launched.

If everything goes well, the Spring Boot logo will be printed to stdout.

## Try some commands

See the `RPC-API.md` file for full list of supported commands.

Assuming that you bound the RPC interface to localhost:12345

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

