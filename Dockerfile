FROM amazoncorretto:17
LABEL authors="https://saifurtech.us/"
CMD ./mvnw -f pom.xml clean package
ARG JAR_FILE=./target/locale-app.jar
COPY ${JAR_FILE} locale-app.jar
EXPOSE 8080
CMD java $JAVA_OPTS -jar /locale-app.jar
