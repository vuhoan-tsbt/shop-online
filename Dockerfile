## build stage ##
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true
## run image ##

FROM amazoncorretto:17-alpine

## tạo thư mục chứa file jar
WORKDIR /run

COPY --from=build /app/target/shop_online-0.0.1-SNAPSHOT.jar  ./shop_online-0.0.1-SNAPSHOT.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "shop_online-0.0.1-SNAPSHOT.jar"]
