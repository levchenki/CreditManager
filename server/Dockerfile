FROM maven:3.8.1-openjdk-11-slim as MAVEN_BUILD
WORKDIR /app-build
ADD pom.xml .
RUN mvn verify clean --fail-never
ADD . .
RUN mvn clean package

FROM openjdk:11-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /app-build/target/credit-manager-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]