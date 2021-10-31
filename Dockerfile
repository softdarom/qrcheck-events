FROM openjdk:11.0.10-jdk

ARG APP_HOME=/opt/events
ARG APP_JAR=events.jar

ENV TZ=Europe/Moscow \
    HOME=$APP_HOME \
    JAR=$APP_JAR

WORKDIR $HOME
COPY build/libs/events.jar $HOME/$JAR
ENTRYPOINT java $JAVA_OPTS -jar $JAR