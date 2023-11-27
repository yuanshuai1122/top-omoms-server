FROM openjdk:17-alpine
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk update && apk add busybox-extras
ADD *.jar /program.jar
CMD java -jar /program.jar