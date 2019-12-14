FROM tomcat:9

ENV SPRING_PROFILES_ACTIVE=dev \
    DATASOURCE_URL=jdbc:postgresql://postgres:5432/pragphil \
    DATASOURCE_USER=postgres \
    DATASOURCE_PASSWORD=postgres \
    CATALINA_HOME=/usr/local/tomcat

# dev only, allows to publish webapps into the running container
COPY ./docker/conf/manager-context.xml $CATALINA_HOME/webapps/manager/META-INF/context.xml
COPY ./docker/conf/tomcat-users.xml $CATALINA_HOME/conf/tomcat-users.xml

COPY target/pragphil.war $CATALINA_HOME/webapps/pragphil.war
