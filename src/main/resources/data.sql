CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    30
),
    email VARCHAR
(
    320
) UNIQUE
    );