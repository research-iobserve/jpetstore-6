#!/bin/bash

function terminate() {
	catalina.sh stop
}

trap terminate SIGINT SIGTERM EXIT

echo $HOSTNAME

cat /server.xml.template | sed "s/%HOSTNAME%/$HOSTNAME/g" > $CATALINA_HOME/conf/server.xml

catalina.sh start

while true ; do
        sleep 1
done
# end