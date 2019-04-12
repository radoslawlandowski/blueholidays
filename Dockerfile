# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# copy WAR into image
COPY build/libs/blueholidays-r-landowski-0.0.1-SNAPSHOT.jar /blueholidays-r-landowski-0.0.1-SNAPSHOT.jar
# run application with this command line 
CMD ["/usr/bin/java", "-jar", "/blueholidays-r-landowski-0.0.1-SNAPSHOT.jar"]