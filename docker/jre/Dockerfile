FROM openjdk:21-jdk-slim
LABEL email=sevendipoluan@gmail.com

WORKDIR /app 

COPY demo-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar"]

CMD ["/app/demo-0.0.1-SNAPSHOT.jar", "--server.port=8080", "--spring.datasource.url=jdbc:postgresql://postgres_server:5432/db_interview"]