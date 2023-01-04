FROM maven:3.8.5-openjdk-17 as build

WORKDIR /opt/app

COPY pom.xml /opt/app/
COPY ./api/pom.xml /opt/app/api/
COPY ./calculations/pom.xml /opt/app/calculations/

COPY ./calculations /opt/app/calculations/
RUN mvn package -DskipTests -pl calculations

FROM openjdk:17-slim

COPY --from=build /opt/app/calculations/target/calculations-*.jar /opt/app.jar
CMD java -jar /opt/app.jar --spring.profiles.active=prod