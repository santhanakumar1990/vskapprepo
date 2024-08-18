FROM openjdk:8
EXPOSE 8080
ADD target/spring-app-first.jar spring-app-first.jar 
ENTRYPOINT ["java","-jar","spring-app-first.jar"]
