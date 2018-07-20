# README

In this archive, the iObserve project and the Software Engineering Group of Kiel
University provide a distributed version of the JPetStore. The application is
divided in one frontend service and three backend services which handle
account management, the catalog and the order operations.

Before we discuss any details, we want to thank the MyBatis team for providing
the JPetStore.

## Requirements

- Java 7 or higher
- git
- Maven 
- docker (optional)
- docker-compose (optional)

## Preparation

After cloning our repository, select one of the branches to get the JPetStore
variant you want to use. this branch is 
`git checkout distributed-jpetstore-iobserve-monitoring`

Before compiling make sure you have the proper probes reachable in your
maven repositories.

Compile JPetStore with
`mvn clean compile package`

While `clean` is not necessary, it is helpful when you compiled it before and
want to sure to start from a clean environment.

## Execution

After compilation and packaging you can deploy all packages in you own tomcat,
but the packages assume to be accessible with the following hostnames:
- frontend
- acccount
- catalog
- order

In case you want to avoid complicated setup, you can use docker and
docker-compose. In that case you can start the complete setup in the `jpetstore-6`
root directory with:

`docker-compose up`

To stop, you shoud use `docker-compose down` in a separate terminal.

## Access

You can access the application via

`http://localhost:8181/jpetstore-frontend`


