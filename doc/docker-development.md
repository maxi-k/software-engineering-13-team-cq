# Local development using docker

To ensure a consistent development environment for every team member,
it is recommended to use docker containers for running certain builds,
as well as the development database. This document serves as a guide
on how to set this up.

## Install and setup Docker

Download and install Docker for your system from
https://www.docker.com/products/docker-desktop or using your package
manager.

This project also uses `docker-compose`. The macOS and Windows docker
applications you just downloaded already include this package. On
linux, it is usually contained in an extra package. If you use a linux
distribution, follow the instructions on
https://docs.docker.com/compose/install/ for specifics.

Make sure to **start the docker daemon** before trying to run any cli
commands. On macOS, this is done by starting the Desktop Application.
On Linux, use `dockerd`, `systemctl` or `service`, depending on your
system setup.

## Run docker

After setting up docker and starting the docker deamon, it is time to
get into the project specific setup. The root folder of the project
should contain a `docker-compose.yml` file, which contains the
configuration for the different containers, networks and services
handled by docker for this project. Run

```sh
docker-compose up -d
```

to start the containers detached from your terminal (leave out the
`-d` to let the processes run attached, in the foreground of your
terminal). The following things will happen:

* docker will download any dependencies required for running the
  containers. This might take some time when the command is first
  executed.
* docker will start a container running an instance of a
  PostgreSQL database server, which accepts connections on the port
  `5432`. There exist default credentials `postgres:example`, for now.
* docker will start a container running an instance of the database
  administration tool adminer, which can be accessed in the browser
  under http://localhost:8080.

That is all for now.
