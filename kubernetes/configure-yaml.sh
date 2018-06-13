#!/bin/bash

if [ "$1" == "" ] ; then
	echo "Missing logger IP and account instances"
	echo "Usage $0 <ip-address> <replicas>"
	exit
fi

if [ "$2" == "" ] ; then
	echo "Missing logger IP and account instances"
	echo "Usage $0 <ip-address> <replicas>"
	exit

fi

cat jpetstore.yaml.template | sed "s/%LOGGER%/$1/g" | sed "s/%ACCOUNT-INSTANCES%/$2/g" > jpetstore.yaml

# end
