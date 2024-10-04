CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    closed_at TIMESTAMP NULL,
    version INT DEFAULT 1 NOT NULL
);

CREATE INDEX idx_account_number ON account(number);

INSERT INTO account (number, type, currency, status, created_at, updated_at, closed_at, version) VALUES
('8800 0000 0000 0008', 'SAVINGSACCOUNT', 'USD', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
('8800 0000 0000 0002', 'CREDITACCOUNT', 'EUR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
('8800 0000 0000 0003', 'TRUSTACCOUNT', 'EUR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
('8800 0000 0000 0004', 'CORPORATEACCOUNT', 'USD', 'BLOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
('8800 0000 0000 0005', 'INVESTMENTACCOUNT', 'EUR', 'CLOSED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);


CREATE TABLE IF NOT EXISTS savings_account (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES account(id),
    last_interest_calculation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT DEFAULT 1 NOT NULL
);

CREATE TABLE IF NOT EXISTS tariff (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    savings_account_id BIGINT REFERENCES savings_account(id),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS rate_history (
     id BIGSERIAL PRIMARY KEY,
     tariff_id BIGINT REFERENCES tariff(id),
     rate DOUBLE PRECISION NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
INSERT INTO account (number, type, currency, status, created_at, updated_at, closed_at, version)
VALUES ('8800 0000 0000 0001', 'SAVINGSACCOUNT', 'USD', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO savings_account (account_id, last_interest_calculation_date, created_at, updated_at, version)
VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO tariff (type, savings_account_id, applied_at)
VALUES ('PREMIUM', 1, CURRENT_TIMESTAMP);

INSERT INTO rate_history (tariff_id, rate, created_at)
VALUES (1, 0.08, CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS free_account_numbers
(
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(30) NOT NULL,
    number VARCHAR(20) UNIQUE NOT NULL,
    version INT DEFAULT 1 NOT NULL
);

INSERT INTO free_account_numbers (type, number, version)
VALUES
    ('CORPORATEACCOUNT', '4200 0000 0000 0111', 1),
    ('SAVINGSACCOUNT', '8800 0000 0000 0011', 1),
    ('SAVINGSACCOUNT', '8800 0000 0000 0012', 1),
    ('SAVINGSACCOUNT', '8800 0000 0000 0013', 1),
    ('SAVINGSACCOUNT', '8800 0000 0000 0014', 1);