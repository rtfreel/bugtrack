-- remove existing tables
DROP TABLE IF EXISTS `step_history`;
DROP TABLE IF EXISTS `step`;
DROP TABLE IF EXISTS `bug_history`;
DROP TABLE IF EXISTS `bug`;
DROP TABLE IF EXISTS `project_history`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `user`;

-- users table
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (id)
);

-- projects table
CREATE TABLE IF NOT EXISTS project (
    id INT AUTO_INCREMENT,
    owner_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES user(id)
);

-- projects history table
CREATE TABLE IF NOT EXISTS project_history (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    project_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    change_time DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- bugs table
CREATE TABLE IF NOT EXISTS bug (
    id INT AUTO_INCREMENT,
    project_id INT NOT NULL,
    reporter_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (reporter_id) REFERENCES user(id)
);

-- bugs history table
CREATE TABLE IF NOT EXISTS bug_history (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    bug_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    change_time DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bug_id) REFERENCES bug(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- bug steps table
CREATE TABLE IF NOT EXISTS step (
    id INT AUTO_INCREMENT,
    bug_id INT NOT NULL,
    sort_order INT NOT NULL,
    description TEXT,
    expected_result TEXT,
    actual_result TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (bug_id) REFERENCES bug(id)
);

