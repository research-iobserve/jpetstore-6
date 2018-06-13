#!/bin/bash

for service in account-service usa-account-service catalog-service frontend-service order-service ; do
	docker build -t jpetstore-$service $service
done

# end
