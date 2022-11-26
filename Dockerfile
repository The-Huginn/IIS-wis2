FROM quay.io/wildfly/wildfly:23.0.2.Final

COPY extensions/modules ${JBOSS_HOME}/modules
COPY target/wis2.war ${JBOSS_HOME}/standalone/deployments

COPY standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
RUN echo -ne "admin=207b6e0cc556d7084b5e2db7d822555c\nxmrkva00=39ec54226f5b2e602cad297042993b74\nxdrevo00=1e6eee32fb819827a1be32a519c55aeb\nxbucht00=e708e5dfe7710372f6f60adbf574ef3a\nxrybar00=201b5d4b088d2a1b91dcb1bffe709849" > ${JBOSS_HOME}/standalone/configuration/application-users.properties
RUN echo -ne "admin=admin,security\nxmrkva00=lector\nxdrevo00=lector\nxbucht00=student\nxrybar00=student" > ${JBOSS_HOME}/standalone/configuration/application-roles.properties

EXPOSE 8080

USER jboss