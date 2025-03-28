CREATE TABLE IF NOT EXISTS account
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL DEFAULT 'MANAGER' CHECK (role IN ('ADMIN', 'MANAGER'))
);
