-- Таблица пользователей
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Таблица сегментов
CREATE TABLE segments
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Таблица связей пользователей и сегментов
CREATE TABLE user_segments
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    segment_id  BIGINT    NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_segment FOREIGN KEY (segment_id) REFERENCES segments (id) ON DELETE CASCADE
);

-- Уникальный индекс на связь user_id и segment_id
CREATE UNIQUE INDEX user_segments_unique_idx ON user_segments (user_id, segment_id);
