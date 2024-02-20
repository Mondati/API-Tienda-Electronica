FROM openjdk:17-jdk-slim
EXPOSE 8086
EXPOSE 8087
ARG JAR_FILE=target/sales-service-0.1.jar
COPY ${JAR_FILE} app_sales.jar
ENTRYPOINT ["java", "-jar", "app_sales.jar"]