CREATE TABLE account
(
    id         bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number     varchar(64) UNIQUE NOT NULL,
    project_id bigint,
    user_id    bigint,
    type       varchar(64)        NOT NULL,
    currency   varchar(64)        NOT NULL,
    status     varchar(64)        NOT NULL,
    created_at timestamptz                 DEFAULT current_timestamp,
    updated_at timestamptz                 DEFAULT current_timestamp,
    closed_at  timestamptz,
    version    bigint                      DEFAULT 1
);

CREATE INDEX idx_account_user_id ON account (user_id);

CREATE TABLE IF NOT EXISTS savings_account (
                                               id                          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                               account_number              varchar(64) UNIQUE NOT NULL,
                                               last_date_percent           timestamptz,
                                               version                     bigint DEFAULT 1,
                                               created_at                  timestamptz DEFAULT current_timestamp,
                                               updated_at                  timestamptz DEFAULT current_timestamp,

                                               CONSTRAINT fk_account_number FOREIGN KEY (account_number) REFERENCES account (number)
);

CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS '
BEGIN
    NEW.updated_at := current_timestamp;
RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE TRIGGER update_account_updated_at
    BEFORE UPDATE ON account
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

INSERT INTO account (number, user_id, type, currency, status)
VALUES (14534523124, 1, 'PERSONAL', 'RUB', 'ACTIVE'),
       (45927037032456, 1, 'PERSONAL', 'RUB', 'ACTIVE'),
       (2397205732457, 2, 'INDIVIDUAL', 'RUB', 'ACTIVE'),
       (328943571239, 2, 'PREPAID', 'USD', 'ACTIVE'),
       (59728975298, 3, 'INDIVIDUAL', 'RUB', 'ACTIVE'),
       (549072784387, 3, 'PERSONAL', 'RUB', 'ACTIVE');

INSERT INTO account (number, project_id, type, currency, status)
VALUES (23892656235, 1, 'BUSINESS', 'RUB', 'ACTIVE'),
       (597283728973, 1, 'BUSINESS', 'RUB', 'ACTIVE'),
       (934762365823, 2, 'BUSINESS', 'RUB', 'ACTIVE'),
       (2385627836527863, 2, 'BUSINESS', 'RUB', 'ACTIVE'),
       (923582365862, 3, 'BUSINESS', 'RUB', 'ACTIVE'),
       (934579023752, 3, 'BUSINESS', 'RUB', 'ACTIVE'),
       (934579038845852, 4, 'BUSINESS', 'RUB', 'BLOCKED'),
       (93457903336434, 4, 'BUSINESS', 'RUB', 'BLOCKED');

CREATE TABLE IF NOT EXISTS free_account_number
(
    account_type VARCHAR(32)      NOT NULL,
    number       VARCHAR(20)      NOT NULL,
    CONSTRAINT pk_free_account_number PRIMARY KEY (account_type, number)
);

CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    count          BIGINT DEFAULT 0                                       NOT NULL,
    account_type   VARCHAR(32)                                            NOT NULL,
    version        BIGINT DEFAULT 1                                       NOT NULL
);

CREATE TABLE IF NOT EXISTS balance
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_id         BIGINT                                   NOT NULL,
    authorized_balance NUMERIC(18, 2) DEFAULT 0.00              NOT NULL,
    actual_balance     NUMERIC(18, 2) DEFAULT 0.00              NOT NULL,
    created_at         TIMESTAMPTZ    DEFAULT current_timestamp NOT NULL,
    updated_at         TIMESTAMPTZ    DEFAULT current_timestamp NOT NULL,
    version            BIGINT                                   NOT NULL,

    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);