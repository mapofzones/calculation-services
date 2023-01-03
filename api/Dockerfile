FROM maven:3.8.5-openjdk-17 as build

WORKDIR /opt
COPY ./echo/*pom.xml /opt/
COPY ./echo /opt/
RUN mvn package -DskipTests

FROM openjdk:17-slim

COPY --from=build /opt/target/api-*.jar /opt/app.jar
CMD java -jar /opt/app.jar --spring.profiles.active=prod