CREATE TABLE IF NOT EXISTS price (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crypto_pair VARCHAR(255) NOT NULL,
    bid_price DECIMAL(19, 4) NOT NULL,
    ask_price DECIMAL(19, 4) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS crypto_user (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    usdt_balance DECIMAL(19, 4),
    btcusdt_balance DECIMAL(19, 4),
    ethusdt_balance DECIMAL(19, 4)
);

INSERT INTO crypto_user (id, username, usdt_balance, btcusdt_balance, ethusdt_balance) VALUES (1, 'TOM', 50000.00, 0.00, 0.00);
INSERT INTO crypto_user (id, username, usdt_balance, btcusdt_balance, ethusdt_balance) VALUES (2, 'DICK', 50000.00, 0.00, 0.00);
INSERT INTO crypto_user (id, username, usdt_balance, btcusdt_balance, ethusdt_balance) VALUES (3, 'HARRY', 50000.00, 0.00, 0.00);

