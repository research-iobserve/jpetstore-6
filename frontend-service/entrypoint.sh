#!/bin/bash

function terminate() {
	catalina.sh stop
}

trap terminate SIGINT SIGTERM EXIT

echo $HOSTNAME

if [ "$LOGGER" == "" ] ; then
	LOGGER="172.17.0.1"
fi

echo "Logger $LOGGER"

echo "$LOGGER logger" >> /etc/hosts

cat /server.xml.template | sed "s/%HOSTNAME%/$HOSTNAME/g" > $CATALINA_HOME/conf/server.xml

catalina.sh start

while true ; do
        sleep 1
done
# end
