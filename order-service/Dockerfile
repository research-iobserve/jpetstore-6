FROM tomcat:8.0-jre8

COPY target/jpetstore-order.war $CATALINA_HOME/webapps/
COPY entrypoint.sh /entrypoint.sh
COPY server.xml.template /server.xml.template

RUN chmod 755 /entrypoint.sh

CMD ["/entrypoint.sh"]