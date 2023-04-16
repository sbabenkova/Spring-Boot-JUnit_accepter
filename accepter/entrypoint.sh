#!/bin/sh
TZ_OPTS=-Duser.timezone=Europe/Moscow
echo "SPRING_PROFILE = ${SPRING_PROFILE:-docker}"
java ${JVM_OPTS} -Djava.security.egd=file:/dev/./urandom ${TZ_OPTS} -Dspring.profiles.active=${SPRING_PROFILE:-docker} -jar /app.jar