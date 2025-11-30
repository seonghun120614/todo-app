#!/bin/bash
#
# desc.: for build convenience
# writer: seonghun120614
# createdAt: 2025-11-27
#

set -euo pipefail

docker-compose down -v
docker-compose up --build -d
docker-compose logs