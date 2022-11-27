#!/bin/bash
cd /home/wis2
mvn package wildfly:deploy &
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0