FROM postgres:16-alpine

# Copy initialization script
COPY init.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432