SELECT 'CREATE DATABASE products'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'products')\gexec

\connect products

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,

    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP
);
