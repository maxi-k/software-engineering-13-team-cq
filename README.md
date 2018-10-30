# SE Master - Software Engineering Project - Team CQ

[![Build Status](https://travis-ci.com/maxi-k/se-master-sem1-st.svg?token=vJCFyC8fzGGkCLryVRQA&branch=master)](https://travis-ci.com/maxi-k/se-master-sem1-st)

## Team "CQ"
- Stefan Grafberger (*Team leader*)
- Swantje Kastrup
- Bernhard Pöttinger
- Fiona Guerin
- Maximilian Kuschewski

## Technologies
| Technologies | Selection     |
| ----------- | -------- |
Language (Backend) | Kotlin
Language (Frontend) | React
Web Framework | Spring Boot
Build Tool | Gradle
ORM | https://github.com/JetBrains/Exposed (falls zu instabil oder nicht complete: http://hibernate.org/) |
Unit Testing | https://github.com/kotlintest/kotlintest,  (Potentiell auch: http://joel-costigliola.github.io/assertj/)
Mocking | https://mockk.io/
IDE | IntelliJ IDEA
| UI Prototyping | moqups.com |
| Issue Tracking | Github
| Code Style / Linter | https://github.com/shyiko/ktlint

## Contributing
- Create issues for everything
- Create branches for issues
- Name branches for issues: `[issue-nr]-[short-description]`
- Rebase and squash before merging
- Before merging any branches into `master`: Two people have to review
  the code.
- Create code, code documentation and issues in English
- Reference the respective issue when creating a pull request

## Ressourcen
* [Spring Boot + Kotlin Microservice](https://kotlinlang.org/docs/tutorials/spring-boot-restful.html)

## How to run the application

### How to run the backend
* Unix: ```./gradlew bootRun```
* Windows: use *gradlew.bat*

### How to run the frontend
Starting a development environment for the frontend with auto
reloading and more can be done using:
```sh
cd calculator/frontend
npm install
npm start
```
Using `yarn` instead of `npm` is recommended.
