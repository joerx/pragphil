FROM tomcat:9

ENV SPRING_PROFILES_ACTIVE=dev \
    DATASOURCE_URL=jdbc:postgresql://postgres:5432/pragphil \
    DATASOURCE_USER=postgres \
    DATASOURCE_PASSWORD=postgres \
    CATALINA_HOME=/usr/local/tomcat

EXPOSE 8080

RUN rm -rf $CATALINA_HOME/webapps/*

COPY ./target/pragphil.war $CATALINA_HOME/webapps/ROOT.war
