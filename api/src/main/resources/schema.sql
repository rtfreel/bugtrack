-- remove existing tables
DROP TABLE IF EXISTS `user`;

-- users table
CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (user_id)
);

