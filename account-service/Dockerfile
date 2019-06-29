FROM tomcat:8.0-jre8

EXPOSE 8080 5791

COPY target/jpetstore-account.war $CATALINA_HOME/webapps/
COPY entrypoint.sh /entrypoint.sh
COPY server.xml.template /server.xml.template
COPY tomcat-users.xml $CATALINA_HOME/conf/

RUN chmod 755 /entrypoint.sh

CMD ["/entrypoint.sh"]
