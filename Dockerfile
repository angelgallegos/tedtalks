FROM gradle:jdk21-alpine
RUN mkdir /home/gradle/buildWorkspace
COPY . /home/gradle/buildWorkspace

WORKDIR /home/gradle/buildWorkspace
RUN ls /home/gradle/buildWorkspace
RUN gradle build --no-daemon
COPY build/libs/tedtalks-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


