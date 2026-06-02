-- ============================================
-- LiKoCouple PostgreSQL 建表脚本
-- ============================================

-- 情侣基础信息表
CREATE TABLE IF NOT EXISTS couple (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    start_date  DATE         NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE couple IS '情侣基础信息表';
COMMENT ON COLUMN couple.id IS '主键';
COMMENT ON COLUMN couple.name IS '情侣名称，如 小刘&小孔';
COMMENT ON COLUMN couple.start_date IS '在一起的日期';
COMMENT ON COLUMN couple.created_at IS '创建时间';

-- 用户表 (user 是 PG 保留字，命名为 users)
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
COMMENT ON TABLE users IS '用户表';
COMMENT ON COLUMN users.id IS '主键';
COMMENT ON COLUMN users.couple_id IS '所属情侣ID';
COMMENT ON COLUMN users.username IS '登录用户名，英文';
COMMENT ON COLUMN users.nickname IS '昵称，如 小刘/小孔';
COMMENT ON COLUMN users.role IS '角色标识：A 或 B';
COMMENT ON COLUMN users.password IS 'BCrypt 加密后的密码';
COMMENT ON COLUMN users.created_at IS '创建时间';

-- 日记表
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
COMMENT ON TABLE diary IS '日记表';
COMMENT ON COLUMN diary.id IS '主键';
COMMENT ON COLUMN diary.couple_id IS '所属情侣ID';
COMMENT ON COLUMN diary.user_id IS '写日记的用户ID';
COMMENT ON COLUMN diary.diary_date IS '日记日期';
COMMENT ON COLUMN diary.mood IS '心情emoji，如 😊😢😍😴';
COMMENT ON COLUMN diary.content IS '日记正文';
COMMENT ON COLUMN diary.images IS '图片列表，JSON数组存Base64';
COMMENT ON COLUMN diary.created_at IS '创建时间';
COMMENT ON COLUMN diary.updated_at IS '更新时间';
CREATE INDEX IF NOT EXISTS idx_diary_couple_date ON diary (couple_id, diary_date);

-- 留言表
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
COMMENT ON TABLE message IS '留言表';
COMMENT ON COLUMN message.id IS '主键';
COMMENT ON COLUMN message.couple_id IS '所属情侣ID';
COMMENT ON COLUMN message.from_id IS '发送者用户ID';
COMMENT ON COLUMN message.to_id IS '接收者用户ID';
COMMENT ON COLUMN message.content IS '留言内容';
COMMENT ON COLUMN message.is_read IS '是否已读：false未读 true已读';
COMMENT ON COLUMN message.created_at IS '创建时间';

-- 愿望清单表
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
COMMENT ON TABLE wish IS '愿望清单表';
COMMENT ON COLUMN wish.id IS '主键';
COMMENT ON COLUMN wish.couple_id IS '所属情侣ID';
COMMENT ON COLUMN wish.user_id IS '创建愿望的用户ID';
COMMENT ON COLUMN wish.title IS '愿望标题';
COMMENT ON COLUMN wish.description IS '愿望详细描述';
COMMENT ON COLUMN wish.is_done IS '是否已实现：false未实现 true已实现';
COMMENT ON COLUMN wish.done_date IS '实现日期';
COMMENT ON COLUMN wish.created_at IS '创建时间';
