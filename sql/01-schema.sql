-- ============================================
-- LiKoCouple 数据库建表脚本
-- 按顺序执行：先建表，再导入初始数据
-- ============================================

-- -------------------------------------------
-- 1. 情侣基础信息表
-- 一对情侣一条记录，存放共有信息
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS couple (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(50)  NOT NULL              COMMENT '情侣名称，如 小刘&小孔',
    start_date  DATE         NOT NULL              COMMENT '在一起的日期',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='情侣基础信息表';

-- -------------------------------------------
-- 2. 用户表
-- 一对情侣有两个用户，通过 couple_id 关联
-- username 用于登录，nickname 用于展示
-- role: A/B 区分两个角色
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS user (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    couple_id   BIGINT       NOT NULL              COMMENT '所属情侣ID',
    username    VARCHAR(30)  NOT NULL              COMMENT '登录用户名，英文',
    nickname    VARCHAR(20)  NOT NULL              COMMENT '昵称，如 小刘/小孔',
    role        CHAR(1)      NOT NULL              COMMENT '角色标识：A 或 B',
    password    VARCHAR(200) NOT NULL              COMMENT 'BCrypt 加密后的密码',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (couple_id) REFERENCES couple(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -------------------------------------------
-- 3. 日记表
-- 两人共享日记，通过 couple_id 隔离
-- images 存 JSON 数组格式的 Base64 图片
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS diary (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    couple_id   BIGINT       NOT NULL              COMMENT '所属情侣ID',
    user_id     BIGINT       NOT NULL              COMMENT '写日记的用户ID',
    diary_date  DATE         NOT NULL              COMMENT '日记日期',
    mood        VARCHAR(5)                         COMMENT '心情emoji，如 😊😢😍😴',
    content     TEXT         NOT NULL              COMMENT '日记正文',
    images      MEDIUMTEXT                        COMMENT '图片列表，JSON数组存Base64',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES user(id),
    INDEX idx_diary_couple_date (couple_id, diary_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日记表';

-- -------------------------------------------
-- 4. 留言表
-- 两人互相留言，is_read 标记是否已读
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS message (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    couple_id   BIGINT       NOT NULL              COMMENT '所属情侣ID',
    from_id     BIGINT       NOT NULL              COMMENT '发送者用户ID',
    to_id       BIGINT       NOT NULL              COMMENT '接收者用户ID',
    content     TEXT         NOT NULL              COMMENT '留言内容',
    is_read     TINYINT(1)   DEFAULT 0             COMMENT '是否已读：0未读 1已读',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (from_id)   REFERENCES user(id),
    FOREIGN KEY (to_id)     REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言表';

-- -------------------------------------------
-- 5. 愿望清单表
-- 记录共同的愿望，is_done 标记是否已实现
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS wish (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    couple_id   BIGINT       NOT NULL              COMMENT '所属情侣ID',
    user_id     BIGINT       NOT NULL              COMMENT '创建愿望的用户ID',
    title       VARCHAR(100) NOT NULL              COMMENT '愿望标题',
    description TEXT                              COMMENT '愿望详细描述',
    is_done     TINYINT(1)   DEFAULT 0             COMMENT '是否已实现：0未实现 1已实现',
    done_date   DATE                              COMMENT '实现日期',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (couple_id) REFERENCES couple(id),
    FOREIGN KEY (user_id)   REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='愿望清单表';
