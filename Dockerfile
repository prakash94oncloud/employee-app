FROM tomcat:jdk11-temurin-jammy
COPY target/employee-app-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps
RUN cp -r /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps
