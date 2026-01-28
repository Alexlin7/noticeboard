-- 02-create-user.sql

-- 1. 建立使用者 alex_user，允許從任何來源 (%) 連線
-- 請將 'user_password' 替換為你想設定的密碼
CREATE USER IF NOT EXISTS 'alex_user'@'%' IDENTIFIED BY 'alexlin7';

-- 2. 授予該使用者對 noticeboard 資料庫的「所有權限」
GRANT ALL PRIVILEGES ON `noticeboard`.* TO 'alex_user'@'%';

-- (選用) 如果你也希望該使用者有權限在 noticeboard 建立 Procedure 或 Function，可以加這行
-- GRANT EXECUTE ON `noticeboard`.* TO 'alex_user'@'%';

-- 3. 重新載入權限表，確保設定立即生效
FLUSH PRIVILEGES;
