FROM quay.io/wildfly/wildfly:23.0.2.Final

COPY extensions/modules ${JBOSS_HOME}/modules
COPY target/wis2.war ${JBOSS_HOME}/standalone/deployments

COPY standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
RUN echo -n "admin=207b6e0cc556d7084b5e2db7d822555c" > ${JBOSS_HOME}/standalone/configuration/application-users.properties
RUN echo -n "admin=admin,security" > ${JBOSS_HOME}/standalone/configuration/application-roles.properties

EXPOSE 8080

USER jboss