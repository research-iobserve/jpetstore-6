#!/bin/bash

echo "--------------------------------"
echo "Before"
echo ""

curl -v http://172.17.0.2:8080/jpetstore-account/request-user?username=d

echo "--------------------------------"
echo "Insert"
echo ""

curl -v -X POST --data "@post-insert-account" http://172.17.0.2:8080/jpetstore-account/insert-account

echo "--------------------------------"
echo "After"
echo ""

curl -v http://172.17.0.2:8080/jpetstore-account/request-user?username=d

# end


