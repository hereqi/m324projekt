CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS kriteriendb;
CREATE DATABASE kriteriendb;
USE kriteriendb;

CREATE USER IF NOT EXISTS 'dev'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON kriteriendb.* TO 'dev'@'%';
FLUSH PRIVILEGES;

CREATE TABLE candidate (
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(256),
    password VARCHAR(256),
    PRIMARY KEY (id)
);

CREATE TABLE project (
    id           BIGINT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(256) NOT NULL,
    candidate_id BIGINT,
    FOREIGN KEY (candidate_id) REFERENCES candidate (id),
    PRIMARY KEY (id)
);

CREATE TABLE criterion (
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(256) NOT NULL,
    title            VARCHAR(2000),
    description      VARCHAR(2000),
    req_sub_crit_GS3 INT NOT NULL,
    req_sub_crit_GS2 INT NOT NULL,
    req_sub_crit_GS1 INT NOT NULL,
    PRIMARY KEY (id)
);

/*<chatgpt>*/
CREATE TABLE project_criterion (
    project_id   BIGINT NOT NULL,
    criterion_id BIGINT NOT NULL,

    PRIMARY KEY (project_id, criterion_id),

    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,

    FOREIGN KEY (criterion_id) REFERENCES criterion (id) ON DELETE CASCADE
);
/*</chatgpt>*/

CREATE TABLE sub_criterion (
    id           BIGINT AUTO_INCREMENT NOT NULL,
    content      VARCHAR(2000) NOT NULL,
    criterion_id BIGINT,
    FOREIGN KEY (criterion_id) REFERENCES criterion (id),
    PRIMARY KEY (id)
);
