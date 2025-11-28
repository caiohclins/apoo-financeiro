-- Create Keycloak database
CREATE DATABASE keycloak_db;

-- Grant privileges to the user
GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO postgres_admin;
GRANT ALL PRIVILEGES ON DATABASE financial_db TO postgres_admin;