FROM java:8
VOLUME /tmp
ADD i-cloud-rbac.jar /i-cloud-rbac.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/i-cloud-rbac.jar"]