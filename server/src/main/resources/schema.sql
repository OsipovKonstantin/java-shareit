DROP TABLE IF EXISTS users, requests, items, bookings, comments;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(30),
    email VARCHAR(320) UNIQUE
    );

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    description  VARCHAR(500)                        NOT NULL,
    requestor_id BIGINT                              NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created      TIMESTAMP WITHOUT TIME ZONE         NOT NULL
    );

CREATE TABLE IF NOT EXISTS items
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    name         VARCHAR(255)                        NOT NULL,
    description  VARCHAR(2000)                       NOT NULL,
    is_available BOOLEAN                             NOT NULL,
    owner_id     BIGINT                              NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    request_id   BIGINT DEFAULT NULL                 REFERENCES requests (id) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS bookings
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    start_date TIMESTAMP WITHOUT TIME ZONE         NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE         NOT NULL,
    status     VARCHAR(30),
    booker_id  BIGINT                              NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    item_id    BIGINT                              NOT NULL REFERENCES items (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    text      VARCHAR(1000)                       NOT NULL,
    item_id   BIGINT                              NOT NULL REFERENCES items (id) ON DELETE CASCADE,
    author_id BIGINT                              NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created   TIMESTAMP WITHOUT TIME ZONE         NOT NULL
    );