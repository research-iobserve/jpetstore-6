#!/bin/bash

BASE_DIR=$(cd "$(dirname "$0")"; pwd)

for I in *-service ; do
	cd $BASE_DIR/$I
	docker build -t jpetstore-$I .
	cd ..
done

# end

