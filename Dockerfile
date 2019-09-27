FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/student.jar /app.jar
ENTRYPOINT ["java","-jar","student.jar"]
 
