FROM tomcat:9

ENV SPRING_PROFILES_ACTIVE=production \
    CATALINA_HOME=/usr/local/tomcat

# Remove default webapps, won't need them
RUN rm -rf $CATALINA_HOME/webapps/manager \
 && rm -rf $CATALINA_HOME/webapps/host-manager \
 && rm -rf $CATALINA_HOME/webapps/examples \
 && rm -rf $CATALINA_HOME/webapps/docs

COPY ./target/pragphil.war $CATALINA_HOME/webapps/pragphil.war
