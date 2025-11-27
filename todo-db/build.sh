#!/bin/bash

VERSION=$1

docker build -t todo-mysql:${VERSION} .