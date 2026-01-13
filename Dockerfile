FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/Will-0.0.1-SNAPSHOT.jar /app/myapp.jar

EXPOSE 8081

CMD ["java", "-jar", "myapp.jar"]