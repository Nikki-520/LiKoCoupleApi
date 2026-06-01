-- ============================================
-- LiKoCouple 初始数据
-- 密码需先用 PasswordUtil.main() 生成 BCrypt 密文后填入
--
-- 生成密文方式（IDE 中右键 PasswordUtil → Run 'main()'）：
--   main(args) 第一个参数是 username，第二个是密码
--   默认: java PasswordUtil liu 123456
-- ============================================

INSERT INTO couple (id, name, start_date) VALUES (1, '小刘&小孔', '2023-04-28');

-- 请分别对每个 username 运行 PasswordUtil.main() 生成密文
INSERT INTO user (id, couple_id, username, nickname, role, password) VALUES
(1, 1, 'liu',  '小刘', 'A', 'PASSWORD_HASH_HERE'),
(2, 1, 'kong', '小孔', 'B', 'PASSWORD_HASH_HERE');
