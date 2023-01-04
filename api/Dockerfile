FROM maven:3.8.5-openjdk-17 as build

WORKDIR /opt/app

COPY pom.xml /opt/app/
COPY ./api/pom.xml /opt/app/api/
COPY ./calculations/pom.xml /opt/app/calculations/

COPY ./api /opt/app/api/
RUN mvn package -DskipTests -pl api

FROM openjdk:17-slim

COPY --from=build /opt/app/api/target/api-*.jar /opt/app.jar
CMD java -jar /opt/app.jar --spring.profiles.active=prod