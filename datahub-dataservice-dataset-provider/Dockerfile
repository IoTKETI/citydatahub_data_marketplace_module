FROM openjdk:8-jdk-alpine
RUN apk --no-cache add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Xss512k", "-Xms6144m", "-Xmx6144m", "-jar","/app.jar"]