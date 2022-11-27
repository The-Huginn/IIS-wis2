FROM quay.io/wildfly/wildfly:23.0.2.Final

COPY extensions/modules ${JBOSS_HOME}/modules
COPY standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
COPY src /home/wis2/src
COPY pom.xml /home/wis2

# Password are generated as following
# md5(username:ApplicationRealm:password) e.g. admin=md5(admin:ApplicationRealm:admin)
# All white characters and trailing new line shall be avoided, otherwise functionality not guaranteed
RUN echo -ne "admin=207b6e0cc556d7084b5e2db7d822555c\nxmrkva00=39ec54226f5b2e602cad297042993b74\nxdrevo00=1e6eee32fb819827a1be32a519c55aeb\nxbucht00=e708e5dfe7710372f6f60adbf574ef3a\nxrybar00=201b5d4b088d2a1b91dcb1bffe709849" > ${JBOSS_HOME}/standalone/configuration/application-users.properties
# List all roles for user without white characters separated by comma
RUN echo -ne "admin=admin,security\nxmrkva00=lector\nxdrevo00=lector\nxbucht00=student\nxrybar00=student" > ${JBOSS_HOME}/standalone/configuration/application-roles.properties

EXPOSE 8080

# Install maven
USER root
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/home/wis2"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# End of maven


COPY customRun.sh /home/wis2
# USER root
RUN chown jboss /home/wis2
USER jboss
CMD ["/home/wis2/customRun.sh"]