FROM openjdk:11.0.3-jdk-stretch
RUN apt-get update && apt-get install netcat-openbsd
RUN mkdir -p /tmp/cicd
ADD build/libs/@name@-@version@.jar /tmp/@name@
ENTRYPOINT ["java","-jar","/tmp/@name@/@name@-@version@.jar"]

