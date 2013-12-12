#!/bin/bash
mvn clean package
export JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n"
chmod +x ./target/bin/webapp
./target/bin/webapp

# flickr key: 89d44bcfe53f4000e716498741a61185
# secret: bbf761d312c32b3f
