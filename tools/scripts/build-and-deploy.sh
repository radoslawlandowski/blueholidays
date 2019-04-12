#!/usr/bin/env bash

echo "Make sure to have set up the proper environment variables!"

./gradlew clean test buildZip && sls deploy