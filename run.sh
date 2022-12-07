# Create network link for database

# Creates PostgreSQL database and runs in detached mode
podman rm -f postgresql

# Install all node.js dependencies and build the frontend
# cd frontend
# npm install
# npm run build
# cd ../

podman run -d \
-p 5432:5432 \
--name postgresql \
--env POSTGRESQL_USER=wis2admin \
--env POSTGRESQL_PASSWORD=wis2Admin \
--env POSTGRESQL_DATABASE=wis2db \
--env TZ='GMT-2' \
--env PGTZ='GMT-2' \
registry.access.redhat.com/rhscl/postgresql-10-rhel7:latest

# Builds our own image for project
podman build -t wis2 .

podman rm -f wis2

# Runs our application in podman in attached mode
podman run \
--name wis2 \
-p 8080:8080 \
--env DB_USER=wis2admin \
--env DB_PWD=wis2Admin \
--env DB_DRIVER=postgresql \
--env DB_HOST=145.239.85.0 \
--env DB_PORT=5432 \
--env DB_DB=wis2db \
wis2:latest
