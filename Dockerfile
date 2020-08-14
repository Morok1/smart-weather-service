FROM openjdk:8

EXPOSE 8080

ADD ./target/smart-weather-service-0.0.1-SNAPSHOT.jar.jar smart-weather-service-0.0.1-SNAPSHOT.jar.jar

ENTRYPOINT ["java","-jar","/smart-weather-service-0.0.1-SNAPSHOT.jar"]