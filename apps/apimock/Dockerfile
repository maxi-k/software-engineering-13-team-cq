FROM openjdk:10-jdk as builder

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
COPY build.gradle settings.gradle gradle.properties /gradle/src/
RUN ./gradlew --no-daemon resolveDependencies

# 3. full gradle build
COPY src /gradle/src/src
RUN ./gradlew --no-daemon build

FROM openjdk:10-jre-slim
VOLUME /tmp
EXPOSE 4042
COPY run.sh .
ENTRYPOINT ["bash", "./run.sh"]
COPY data /data
COPY --from=builder /gradle/src/build/libs/apimock.jar app.jar
