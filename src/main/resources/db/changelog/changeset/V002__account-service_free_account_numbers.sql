CREATE TABLE free_account_numbers (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number VARCHAR(20) CHECK(LENGTH(number) >= 12) NOT NULL UNIQUE,
    type VARCHAR(24) NOT NULL
);