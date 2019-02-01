JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Djava.security.egd=file:/dev/./urandom"

if [[ ! -z "$SERVER_SSL_TRUST_STORE" ]]; then
    echo Starting server with custom truststore $SERVER_SSL_TRUST_STORE
    JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStore=$SERVER_SSL_TRUST_STORE -Djavax.net.ssl.trustStorePassword=changeit"
fi

if [[ ! -z "$LOGGING_CONFIG" ]]; then
    JAVA_OPTS="$JAVA_OPTS -Dlogging.config=$LOGGING_CONFIG"
fi

java $JAVA_OPTS -jar /app.jar