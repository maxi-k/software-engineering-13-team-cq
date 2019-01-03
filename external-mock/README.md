# BMW FleetData

## Contents

- `apps/`: contains all deployables; contains all services of the FleetData application as well as services for testing, monitoring and local development
- `env/`: environement configuration files for local/test/int/prod deployments
- `scripts/`: usefull scripts for common tasks
- `docker-compose.yaml`: use this compose configuration to start the application locally
- `Passwords.kdbx`: [KeePass](keepass.info) encrypted Password-Database containing all relevant passwords

## Local Development

### Build & Run

There is a `docker-compose.yaml` file that can be used to deploy the service locally. 
Build and deploy by running following commands in the root directory.

```
docker-compose up --build
```

To remove the local services with all data use 
```
docker-compose down -v
```

## Services

Each service is documented in its own README.