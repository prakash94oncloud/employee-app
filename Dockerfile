FROM tomcat:9.0-jdk11
COPY target/employee-app-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps
RUN cp -r /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps
