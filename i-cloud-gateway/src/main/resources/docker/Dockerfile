FROM java:8
VOLUME /tmp
ADD i-cloud-gateway.jar /i-cloud-gateway.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/i-cloud-gateway.jar"]