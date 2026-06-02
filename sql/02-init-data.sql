-- ============================================
-- LiKoCouple PostgreSQL 初始数据
-- 密码需先用 PasswordUtil.main() 生成 BCrypt 密文后填入
-- ============================================

INSERT INTO couple (id, name, start_date) VALUES (1, '小刘&小孔', '2023-04-28');

INSERT INTO users (id, couple_id, username, nickname, role, password) VALUES
(1, 1, 'liu',  '小刘', 'A', 'PASSWORD_HASH_HERE'),
(2, 1, 'kong', '小孔', 'B', 'PASSWORD_HASH_HERE');

-- 重置序列，防止后续 INSERT 不自增 id 时报重复
SELECT setval('couple_id_seq', (SELECT MAX(id) FROM couple));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
