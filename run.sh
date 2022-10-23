# Creates PostgreSQL database and runs in detached mode
podman rm -f postgresql

podman run -d \
-p 5432:5432 \
--name postgresql \
--env POSTGRESQL_USER=wis2admin \
--env POSTGRESQL_PASSWORD=wis2Admin \
--env POSTGRESQL_DATABASE=wis2db \
--env TZ='GMT-2' \
--env PGTZ='GMT-2' \
registry.redhat.io/rhel8/postgresql-10:latest

# Builds our project
mvn clean package

# Builds our own image for project
podman build -t wis2 .

# Runs our application in podman in attached mode
podman rm -f wis2

podman run \
-p 8080:8080 \
--name wis2 \
wis2:latest