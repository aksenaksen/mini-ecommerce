FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY target/config-server-1.0.jar ConfigServer.jar
ENTRYPOINT ["java","-jar","ConfigServer.jar"]