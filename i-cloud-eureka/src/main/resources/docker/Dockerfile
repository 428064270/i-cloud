FROM java:8
VOLUME /tmp
ADD i-cloud-eureka.jar /i-cloud-eureka.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/i-cloud-eureka.jar"]