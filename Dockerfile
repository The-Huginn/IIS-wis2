FROM registry.redhat.io/jboss-eap-7/eap74-openjdk11-openshift-rhel8:7.4.7-3

COPY /modules ${JBOSS_HOME}/modules
COPY target/wis2.war ${JBOSS_HOME}/standalone/deployments

ENV DISABLE_EMBEDDED_JMS_BROKER true
EXPOSE 8080

COPY standalone-openshift.xml ${JBOSS_HOME}/standalone/configuration/standalone-openshift.xml