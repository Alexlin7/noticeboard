CREATE TABLE notice_image (
    id BINARY(16) PRIMARY KEY COMMENT 'Primary Key, UUIDv7',
    notice_id BINARY(16) NOT NULL COMMENT 'Foreign key to the notice table',
    file_path VARCHAR(512) NOT NULL COMMENT 'Relative path to the stored image file',
    original_filename VARCHAR(255) COMMENT 'The original filename from the user',
    file_size BIGINT NOT NULL COMMENT 'File size in bytes',
    mime_type VARCHAR(100) COMMENT 'MIME type of the file',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of creation',
    FOREIGN KEY (notice_id) REFERENCES notice(id) ON DELETE CASCADE
) COMMENT = 'Stores information about images uploaded for notices';

CREATE INDEX idx_notice_image_notice_id ON notice_image (notice_id);
