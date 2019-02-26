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
* docker will start a container for every folder in `apps/`, and
  connect them as defined by `docker-compose.yml`
* The applications should now run and be usable under ports `3000`,
  `8080` and `4040`, for the cns-frontend, cns-backend and mocks, respectively

## Helper Scripts
There are three helper scripts for dedicated frontend, backend and
fullstack development under `scripts/`. They start every container as
described above, except for the one that runs the part of the
application you want to work on. For example, if you want to develop
frontend and backend, thus starting them externally in your favorite
IDE, execute `scripts/fullstack-dev`. This will start all required
mocks and the database.

## Delete the Database
The docker store defines the name of the volume where the data of the
database container is stored as `db_1`. If there was some
breaking change to the database schema during development that can't
be fixed with a migration with a reasonable amount of effort, you can
delete the data stored in the docker volume. A description of how to
do that follows:
1. Stop the database container:
```sh
docker ps
# Select the container-id of the database container
docker stop [container-id]
```
2. Delete the database container:
Because the database container has the data volume as a dependency, it
needs to be deleted before the volume can be deleted.
```sh
docker rm [container-id]
```
3. Delete the volume
```sh
docker volume ls
# Select th volume-id of volume used by the container, probably sample_db
docker volume rm [volume-id]
```

That is all for now.
