#/bin/bash

CURL="/usr/bin/curl"

REQUESTS="insert-account update-account request-user"

for I in $REQUESTS ; do
	if [ -f "post-$I" ] ; then
		echo "post $I"
		$CURL --data "@post-$I" -H "Accept: application/json" -X POST "http://172.17.0.2:8080/jpetstore-account/$I"
	elif [ -f "get-$I" ] ; then
		echo "get $I"
		PARAMETERS=`cat "get-$I"`
		$CURL -v -X GET "http://172.17.0.2:8080/jpetstore-account/$I?$PARAMETERS"
	else
		echo "no post file $I"
	fi
done

# end

