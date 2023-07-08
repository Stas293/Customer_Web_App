FROM maven:3.9.3 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY redisson /home/app/redisson
RUN mvn -f /home/app/pom.xml clean package -DskipTests


FROM openjdk:20
COPY --from=build /home/app/target/Customer_Web_App-0.0.1-SNAPSHOT.jar /usr/local/lib/Customer_Web_App-0.0.1-SNAPSHOT.jar
COPY --from=build /home/app/redisson/redisson-dev.yaml /usr/local/lib/redisson/redisson-dev.yaml
EXPOSE 8080

WORKDIR /usr/local/lib
ENV REDIS_HOSTNAME=redis
ENTRYPOINT ["java","--enable-preview","-jar","/usr/local/lib/Customer_Web_App-0.0.1-SNAPSHOT.jar"]
