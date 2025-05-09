CREATE TABLE IF NOT EXISTS sessions (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_key VARCHAR(255) NOT NULL UNIQUE,
    theme VARCHAR(255) NOT NULL,
    number_of_alternatives INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    current_question_key INT NOT NULL,
    status ENUM('CREATED', 'ONGOING', 'COMPLETED') NOT NULL DEFAULT 'CREATED'
);

INSERT IGNORE INTO sessions (session_id, session_key, theme, number_of_alternatives, username, current_question_key)
VALUES (1, 'MOCKQUIZ', 'History', 4, 'MockUser', 0);