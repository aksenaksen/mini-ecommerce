FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp

COPY target/discovery-1.0.jar DiscoveryService.jar

ENV JAVA_TOOL_OPTIONS="--enable-preview"

ENTRYPOINT ["java","-jar","DiscoveryService.jar"]