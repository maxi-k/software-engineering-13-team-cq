FROM openjdk:10-jdk as builder
RUN echo "echo \"hello world\"" > run.sh
RUN chmod -x run.sh


FROM openjdk:10-jre-slim
ENTRYPOINT ["bash", "./run.sh"]
COPY --from=builder /run.sh /
