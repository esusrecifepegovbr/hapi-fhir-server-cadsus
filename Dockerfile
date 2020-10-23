FROM maven:latest
WORKDIR /app
COPY . /app
RUN mvn dependency:go-offline
EXPOSE 80
