#!/usr/bin/env bash

echo "Make sure to have set up BH_APP_CAL_API_KEY env variable containing a valid Calendarific api key!"

./gradlew clean test buildZip && sls deploy