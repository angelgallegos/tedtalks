FROM gradle:jdk21-alpine as build
COPY . /app

#COPY build.gradle /app

WORKDIR /app
RUN gradle build --no-daemon
FROM openjdk:21
COPY --from=build /app/build/libs/*.jar /app/app.jar

WORKDIR /app

RUN pwd
RUN ls /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]