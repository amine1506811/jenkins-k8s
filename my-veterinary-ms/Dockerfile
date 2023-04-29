FROM openjdk:11-jdk-slim
WORKDIR /my-veterinary-ms
ADD target/my-veterinary-ms-1.0-SNAPSHOT.jar my-veterinary-ms-1.0-SNAPSHOT.jar
EXPOSE 8092
EXPOSE 35729
ENTRYPOINT [ "java", "-jar", "my-veterinary-ms-1.0-SNAPSHOT.jar" ]