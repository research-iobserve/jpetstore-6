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

LOG=/usr/local/tomcat/logs/catalina.out

while [ ! -f $LOG ] ; do
	echo log
	sleep 1
done
tail -f $LOG
#while true ; do
#        sleep 1
#done
# end
