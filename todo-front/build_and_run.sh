#!/bin/bash

VERSION=$1
CONTAINER_NAME=$2
DO_NOT_RUN=$3
PROFILE=$4

docker rm ${CONTAINER_NAME} || docker rmi todo-front:${VERSION}
docker build -t todo-front:${VERSION} .

if [ -z "$DO_NOT_RUN" ]; then
    docker run -d --name ${CONTAINER_NAME} -p 80:80 -e PROFILE=${PROFILE:-dev} todo-front:${VERSION}

    docker logs ${CONTAINER_NAME}
fi