FROM gradle:jdk21-alpine AS build

WORKDIR /backend

COPY gradle.properties settings.gradle.kts gradlew ./
COPY backend ./

RUN --mount=type=cache,target=/root/.gradle gradle --no-daemon clean bootJar

FROM eclipse-temurin:21-jdk-alpine

RUN mkdir /app

COPY --from=build /backend/build/libs/*.jar /app/application.jar

EXPOSE 8080

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "/app/application.jar"]