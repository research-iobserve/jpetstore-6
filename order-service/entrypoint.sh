#!/bin/bash

function terminate() {
	echo "Terminating..."
	catalina.sh stop
	echo "Done."
}

trap terminate SIGINT SIGTERM EXIT

echo "order host $HOSTNAME"

if [ "$LOGGER" == "" ] ; then
	LOGGER="172.17.0.1"
fi

echo "Logger $LOGGER"

echo "$LOGGER logger" >> /etc/hosts

if [ "$LOCATION" != "" ] ; then
	echo "$LOCATION" >> /usr/local/tomcat/country-code.txt
fi

cat /server.xml.template | sed "s/%HOSTNAME%/$HOSTNAME/g" > $CATALINA_HOME/conf/server.xml

catalina.sh start
while true ; do
	sleep 1
done

# end
