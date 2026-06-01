CREATE TABLE couple (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50)  NOT NULL,
    start_date  DATE         NOT NULL,
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    couple_id   BIGINT       NOT NULL,
    username    VARCHAR(30)  NOT NULL,
    nickname    VARCHAR(20)  NOT NULL,
    role        CHAR(1)      NOT NULL,
    password    VARCHAR(200) NOT NULL,
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id)
);

CREATE TABLE diary (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    couple_id   BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    diary_date  DATE         NOT NULL,
    mood        VARCHAR(5),
    content     TEXT         NOT NULL,
    images      MEDIUMTEXT,
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES user(id),
    INDEX idx_diary_couple_date (couple_id, diary_date)
);

CREATE TABLE message (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    couple_id   BIGINT       NOT NULL,
    from_id     BIGINT       NOT NULL,
    to_id       BIGINT       NOT NULL,
    content     TEXT         NOT NULL,
    is_read     TINYINT(1)   DEFAULT 0,
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (from_id)   REFERENCES user(id),
    FOREIGN KEY (to_id)     REFERENCES user(id)
);

CREATE TABLE wish (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    couple_id   BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    is_done     TINYINT(1)   DEFAULT 0,
    done_date   DATE,
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES user(id)
);
