FROM java:8
VOLUME /tmp
ADD i-cloud-auth.jar /i-cloud-auth.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/i-cloud-auth.jar"]