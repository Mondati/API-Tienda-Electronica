FROM openjdk:17-jdk-slim
EXPOSE 8084
EXPOSE 8085
ARG JAR_FILE=target/carts-service-0.1.jar
COPY ${JAR_FILE} app_cart.jar
ENTRYPOINT ["java", "-jar", "app_cart.jar"]