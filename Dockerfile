FROM openjdk:8-jre

RUN mkdir /spring-boot-journal

EXPOSE 12000

WORKDIR /spring-boot-journal

COPY Dockerfile .
COPY target/*.jar /spring-boot-journal

CMD java -Djava.security.egd=file:/dev/./urandom -jar *.jar 
