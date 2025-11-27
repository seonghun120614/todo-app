#!/bin/bash
#
# desc.: for build convenience
# writer: seonghun120614
# createdAt: 2025-11-27
#

set -euo pipefail

BACK_SERVER_NAME="todo-back"
FRONT_SERVER_NAME="todo-front"
DB_SERVER_NAME="todo-mysql"

VERSION=0.1

docker-compose down -v

# mysql
cd todo-db && \
    chmod u+x ./build.sh && \
    ./build.sh "${VERSION}" && \
cd ..

# back
cd todo-back && \
    chmod u+x ./gradlew && \
    ./gradlew bootBuildImage -PimageName="${BACK_SERVER_NAME}:${VERSION}" && \
cd ..

# front
cd todo-front && \
    ./build_and_run.sh "${VERSION}" "${FRONT_SERVER_NAME}" "test" "true" && \
cd ..

# up
docker-compose up -d