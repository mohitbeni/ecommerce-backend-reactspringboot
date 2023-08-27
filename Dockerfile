FROM openjdk:17-jdk-slim-buster
VOLUME /app                   # Temporary location to run
EXPOSE 8080                   # Provide port number
ADD target/ecommerce-site-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","ecommerce-site-0.0.1-SNAPSHOT.jar"]