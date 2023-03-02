CREATE DATABASE wordsdb;
\c wordsdb;
CREATE TABLE IF NOT EXISTS words
(
    id                      uuid                    PRIMARY KEY,
    word                    VARCHAR(100)    NOT NULL,
    initialization_time     TIMESTAMP       NOT NULL,
    receive_time            TIMESTAMP       NOT NULL,
    db_creation_time        TIMESTAMP       NOT NULL
);