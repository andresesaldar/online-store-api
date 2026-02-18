CREATE DATABASE products;

CREATE TABLE product (
    id BIGINT PRIMARY KEY,

    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP
);
