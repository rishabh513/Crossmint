FROM openjdk:17-jdk-slim
LABEL authors="duckitup"
COPY target/crossmint-0.0.1-SNAPSHOT.jar ./crossmint-0.0.1-SNAPSHOT.jar
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "crossmint-0.0.1-SNAPSHOT.jar"]