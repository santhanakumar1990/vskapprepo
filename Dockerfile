FROM openjdk:17-jdk
EXPOSE 8080
ADD target/spring-app-second.jar spring-app-second.jar 
ENTRYPOINT ["java","-jar","spring-app-second.jar"]
