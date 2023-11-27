FROM openjdk:17-alpine

RUN apk update \
    && apk add busybox-extras \
RUN cd / \
    && mkdir data
COPY target/*.jar /data/program.jar

EXPOSE 8081

CMD ["java","-Xmx512m","-Xms512m","-jar","/data/program.jar"]