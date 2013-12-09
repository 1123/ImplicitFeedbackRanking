#!/bin/bash
mvn clean package
export JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n"
chmod +x ./target/bin/webapp
./target/bin/webapp
