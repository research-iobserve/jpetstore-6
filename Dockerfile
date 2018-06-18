FROM tomcat:8.0-jre8

COPY target/jpetstore.war $CATALINA_HOME/webapps/
COPY entrypoint.sh /entrypoint.sh
COPY server.xml.template /server.xml.template
COPY logging.properties $CATALINA_HOME/conf/

RUN chmod 755 /entrypoint.sh

CMD ["/entrypoint.sh"]
