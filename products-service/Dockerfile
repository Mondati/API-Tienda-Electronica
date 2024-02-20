FROM openjdk:17-jdk-slim
EXPOSE 8082
EXPOSE 8083
ARG JAR_FILE=target/products-service-0.1.jar
COPY ${JAR_FILE} app_products.jar
ENTRYPOINT ["java", "-jar", "app_products.jar"]
