# Build
FROM eclipse-temurin:17-jdk-focal AS build
ENV HOME=/app

RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME

RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package -Dmaven.test.skip


# Run
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/app/target/*.jar

COPY --from=build $JAR_FILE /app/runner.jar

RUN mkdir -p /app/mediacache
RUN chmod -R 755 /app/mediacache

ENTRYPOINT java -jar /app/runner.jar