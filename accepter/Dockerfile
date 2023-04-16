ARG DOCKER_PREFIX
FROM ${DOCKER_PREFIX}/openjdk:11-jre-slim
VOLUME /tmp
ENV ACCEPTER_PORT=20019
RUN apt update && apt install -y git
COPY target/accepter.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]