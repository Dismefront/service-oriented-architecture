#!/bin/bash

# Script to run both instances of the navigator service

echo "Starting primary instance on port 23169..."
./gradlew :web:bootRun > primary.log 2>&1 &

PRIMARY_PID=$!

echo "Starting secondary instance on port 23170..."
SPRING_PROFILES_ACTIVE=second ./gradlew :web:bootRun > secondary.log 2>&1 &

SECONDARY_PID=$!

echo "Primary instance PID: $PRIMARY_PID"
echo "Secondary instance PID: $SECONDARY_PID"

echo "Both instances started. Check primary.log and secondary.log for logs."

# Trap SIGINT to kill both processes
trap 'kill $PRIMARY_PID $SECONDARY_PID; exit' INT

# Wait for both processes
wait $PRIMARY_PID $SECONDARY_PID