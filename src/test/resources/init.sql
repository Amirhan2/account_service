CREATE TABLE IF NOT EXISTS free_account_number
(
    account_type VARCHAR(20)      NOT NULL,
    number       BIGINT      NOT NULL,
    CONSTRAINT pk_free_account_number PRIMARY KEY (account_type, number)
    );

CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    count          BIGINT DEFAULT 1                                       NOT NULL,
    account_type   VARCHAR(20)                                            NOT NULL,
    version        BIGINT DEFAULT 1                                       NOT NULL
    );