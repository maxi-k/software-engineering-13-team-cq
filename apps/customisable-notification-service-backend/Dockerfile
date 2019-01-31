FROM openjdk:8-jdk as builder

ARG http_proxyHost
ARG http_proxyPort
ARG https_proxyHost
ARG https_proxyPort
ARG http_proxyUser
ARG http_proxyPassword
ARG http_nonProxyHosts

# 1. download gradle wrapper
COPY gradlew /gradle/src/
COPY gradle/wrapper/* /gradle/src/gradle/wrapper/
WORKDIR /gradle/src
ENV GRADLE_USER_HOME=/gradle
RUN echo "systemProp.http.proxyHost=$http_proxyHost\nsystemProp.http.proxyPort=$http_proxyPort\nsystemProp.https.proxyHost=$https_proxyHost\nsystemProp.https.proxyPort=$https_proxyPort\nsystemProp.http.proxyUser=$http_proxyUser\nsystemProp.http.proxyPassword=$http_proxyPassword\nsystemProp.http.nonProxyHosts=$http_nonProxyHosts" >> /gradle/gradle.properties
RUN ./gradlew --version

# 2. download all dependencies
COPY build.gradle gradle.properties /gradle/src/
COPY gradle/install-git-hooks.gradle /gradle/src/gradle/
RUN ./gradlew --no-daemon resolveDependencies

# 3. full gradle build
COPY src /gradle/src/src
RUN ./gradlew --no-daemon -Pversion=docker bootJar
RUN ls /gradle/src/build/libs

FROM openjdk:8-jre-slim
VOLUME /tmp
EXPOSE 8080
COPY --from=builder /gradle/src/build/libs/src-docker.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# ARG GRADLE_VERSION=4.10.2
# ARG JAVA_VERSION=8-alpine
#
# FROM gradle:${GRADLE_VERSION}-jdk${JAVA_VERSION} as BUILD
#
# COPY . /src
# WORKDIR /src
#
# RUN gradle --version
# RUN gradle --no-daemon resolveDependencies
# RUN gradle --no-daemon -Pversion=docker bootJar --info --debug
#
# FROM gradle:${GRADLE_VERSION}-jre${JAVA_VERSION}
#
# COPY --from=BUILD /src/build/libs/customisable-notification-service-backend-docker.jar /bin/runner/run.jar
# WORKDIR /bin/runner
#
# CMD ["java","-jar","run.jar"]
