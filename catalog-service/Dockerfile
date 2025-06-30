FROM openjdk:21-ea-11-jdk-slim
VOLUME /tmp

COPY target/catalog-service-1.0.jar CatalogService.jar

#ENV SPRING_DATASOURCE_URL="jdbc:mariadb://mariadb:3306/mydb"

ENV JAVA_TOOL_OPTIONS="--enable-preview"

ENV SPRING_CLOUD_CONFIG_URI="http://config-service:8888"

#ENV SPRING_RABBITMQ_HOST="rabbitmq"

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://discovery-service:8761/eureka"


ENTRYPOINT ["java","-jar","CatalogService.jar"]