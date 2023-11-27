FROM openjdk:17-jdk

WORKDIR /app

COPY target/*.jar /app/program.jar

EXPOSE 8081

CMD ["java","-Xmx512m","-Xms512m","-jar","/app/program.jar"]