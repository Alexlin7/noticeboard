CREATE TABLE notice (
    id BINARY(16) PRIMARY KEY COMMENT '主鍵，使用 UUIDv7',
    title VARCHAR(255) NOT NULL COMMENT '公告標題',
    announcer VARCHAR(100) NOT NULL COMMENT '公布者',
    content TEXT NOT NULL COMMENT '公告內容',
    publication_date DATE NOT NULL COMMENT '發佈日期',
    end_date DATE NOT NULL COMMENT '截止日期',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後更新時間',
    `commit` VARCHAR(255) DEFAULT NULL COMMENT '提交信息',
    `index` VARCHAR(255) DEFAULT NULL COMMENT '索引信息'
) COMMENT = '公告欄訊息表';

CREATE INDEX idx_notice_title ON notice (title);
CREATE INDEX idx_notice_publication_date ON notice (publication_date);
CREATE INDEX idx_notice_end_date ON notice (end_date);