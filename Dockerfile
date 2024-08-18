FROM openjdk:8
EXPOSE 8080
ADD target/vskapp.jar vskapp.jar 
ENTRYPOINT ["java","-jar","/vskapp.jar"]
