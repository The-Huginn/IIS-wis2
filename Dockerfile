FROM registry.redhat.io/jboss-eap-7/eap74-openjdk11-openshift-rhel8:7.4.7-3

# COPY extensions/modules ${JBOSS_HOME}/modules

# See S2I_IMAGE_SOURCE_MOUNTS
# COPY extensions /tmp/s2i
# ENV S2I_IMAGE_SOURCE_MOUNTS /tmp/s2i

# See maven_build and S2I_SOURCE_DIR
# COPY src /tmp/src
# COPY pom.xml /tmp

COPY src ${HOME}/tmp/wis2/src
COPY pom.xml ${HOME}/tmp/wis2

USER root
RUN cd ${HOME}/tmp/wis2 && mvn package install

USER root
RUN cp ${HOME}/tmp/target/wis2.war ${JBOSS_HOME}/standalone/deployments

# USER root
# RUN cd /tmp/wis2 && mvn clean package

# USER root
# RUN mv /tmp/wis2/targer/wis2.war ${JBOSS_HOME}/standalone/deployments

ENV DISABLE_EMBEDDED_JMS_BROKER true
COPY standalone-openshift.xml ${JBOSS_HOME}/standalone/configuration/standalone-openshift.xml

RUN echo "admin=207b6e0cc556d7084b5e2db7d822555c" > ${JBOSS_HOME}/standalone/configuration/application-users.properties
RUN echo "admin=admin,security" > ${JBOSS_HOME}/standalone/configuration/application-roles.properties

EXPOSE 8080

USER jboss