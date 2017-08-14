FROM tomcat:8.0-jre8

COPY target/jpetstore.war $CATALINA_HOME/webapps/

CMD ["catalina.sh", "run"]
