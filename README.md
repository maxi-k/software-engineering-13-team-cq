# SE Master - Software Engineering Project - Team CQ

[![Build Status](https://travis-ci.com/maxi-k/se-master-sem1-st.svg?token=vJCFyC8fzGGkCLryVRQA&branch=master)](https://travis-ci.com/maxi-k/se-master-sem1-st)

## Contents
- `apps/`: contains all deployables; contains all services of the FleetData application as well as services for testing, monitoring and local development
- `env/`: environement configuration files for local/test/int/prod deployments
- `scripts/`: usefull scripts for common tasks
- `docker-compose.yaml`: use this compose configuration to start the application locally
- `Passwords.kdbx`: [KeePass](keepass.info) encrypted Password-Database containing all relevant passwords

## Technical Documentation
- [Backend](./doc/technische-dokumentation/backend-development.md)
- [Frontend](./doc/technische-dokumentation/frontend-development.md)
- [Docker (Local)](./doc/technische-dokumentation/docker-development.md)

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

## The CNS App
The App is divided into a frontend and a backend. They are deployed to
Heroku automatically if the CI is done and all tests pass.
* Frontend: https://unia-se-teamcq-cns-frontend.herokuapp.com/ 
* Backend: https://unia-se-teamcq-cns-backend.herokuapp.com/
* Api-Mock: https://unia-se-teamcq-apimock-backend.herokuapp.com/

When first accessing those services, there might be a short delay, as
Heroku has to spin them up first.

## Team "CQ"
- Stefan Grafberger (*Team leader*)
- Fiona Guerin
- Maximilian Kuschewski

## Technologies
| Technologies | Selection     |
| ----------- | -------- |
Language (Backend) | Kotlin
Web Framework | Spring Boot
Build Tool | Gradle
ORM | Hibernate
Database Migration | Flyway
Job Scheduling | Quartz
API Client Generation | OpenAPITools
DTO & Entity Mapping | MapStruct
Unit Testing | https://github.com/kotlintest/kotlintest
Mocking | https://mockk.io/
IDE | IntelliJ IDEA
UI Prototyping | Figma |
Issue Tracking | Github
Code Style / Linter | https://github.com/shyiko/ktlint
Language (Frontend) | Typescript & React
Frontend State | Redux
Frontend Testing | Jest, Snapshots & Selenium 

## Contributing
- Create issues for everything
- Create branches for issues
- Name branches for issues: `[issue-nr]-[short-description]`
- Rebase and squash before merging
- Before merging any branches into `master`: Two people have to review
  the code.
- Create code, code documentation and issues in English
- Reference the respective issue when creating a pull request

## Links
- Mockup: https://www.figma.com/file/eoajJpSOmiDUlgJLYVdil0/Notification-Service-Mockup?node-id=29%3A211

## Resources
* [Spring Boot + Kotlin Microservice](https://kotlinlang.org/docs/tutorials/spring-boot-restful.html)

### How to run the backend
In `apps/customisable-notification-service-backend`
* Unix: ```./gradlew bootRun```
* Windows: use *gradlew.bat*

### How to run the frontend
Starting a development environment for the frontend with auto
reloading and more can be done using:
```sh
cd apps/customisable-notification-service-frontend
yarn install
yarn start
```
