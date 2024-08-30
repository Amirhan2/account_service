CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    owner VARCHAR(20) NOT NULL,
    type VARCHAR(50) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    closed_at TIMESTAMP NULL,
    version INT DEFAULT 1 NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_account_owner ON account(owner);
CREATE INDEX IF NOT EXISTS idx_account_number ON account(number);