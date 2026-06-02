CREATE TABLE IF NOT EXISTS couple (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    start_date  DATE         NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL    PRIMARY KEY,
    couple_id   BIGINT       NOT NULL,
    username    VARCHAR(30)  NOT NULL,
    nickname    VARCHAR(20)  NOT NULL,
    role        CHAR(1)      NOT NULL,
    password    VARCHAR(200) NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id)
);

CREATE TABLE IF NOT EXISTS diary (
    id          BIGSERIAL    PRIMARY KEY,
    couple_id   BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    diary_date  DATE         NOT NULL,
    mood        VARCHAR(5),
    content     TEXT         NOT NULL,
    images      TEXT,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_diary_couple_date ON diary (couple_id, diary_date);

CREATE TABLE IF NOT EXISTS message (
    id          BIGSERIAL    PRIMARY KEY,
    couple_id   BIGINT       NOT NULL,
    from_id     BIGINT       NOT NULL,
    to_id       BIGINT       NOT NULL,
    content     TEXT         NOT NULL,
    is_read     BOOLEAN      DEFAULT FALSE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (from_id)   REFERENCES users(id),
    FOREIGN KEY (to_id)     REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS wish (
    id          BIGSERIAL    PRIMARY KEY,
    couple_id   BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    is_done     BOOLEAN      DEFAULT FALSE,
    done_date   DATE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES users(id)
);
