FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/techspec.jar
WORKDIR /opt/app
COPY ${JAR_FILE} techspec.jar
ENTRYPOINT ["java","-jar","techspec.jar"]