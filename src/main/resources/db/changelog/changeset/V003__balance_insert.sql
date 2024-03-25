CREATE TABLE balance (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id bigint NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    authorization_balance DECIMAL(19, 2) NOT NULL,
    actual_balance DECIMAL(19, 2) NOT NULL,
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp,
    version bigint NOT null,
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id)
);