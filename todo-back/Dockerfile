FROM gradle:9.2.1-jdk21-corretto AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21.0.9_10-jre-ubi9-minimal
WORKDIR /app
COPY --from=build /app/build/libs/*-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]