CREATE TABLE account
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number VARCHAR(20) NOT NULL,
    owner_type VARCHAR(20) NOT NULL,
    owner_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    currency CHARACTER(3)  NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    closed_at TIMESTAMP,
    version INT NOT NULL
);

CREATE INDEX idx_owner_type ON account(owner_type);
CREATE INDEX idx_number ON account(number);