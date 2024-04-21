FROM eclipse-temurin:21 AS build
RUN apt update && \
    apt-get install -y net-tools netcat
WORKDIR /app
COPY gradle gradle
COPY src src
COPY build.gradle gradlew settings.gradle ./
RUN ./gradlew build
RUN mv build/libs/*.jar app.jar
RUN ls | grep -xv "app.jar" | xargs rm -rf

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /app/app.jar app.jar
COPY wait-for wait-for
EXPOSE 8080
CMD ["./wait-for", "db:3306", "--", "java", "-jar", "app.jar"]
