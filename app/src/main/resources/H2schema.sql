DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls(
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_checks(
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    url_id BIGINT REFERENCES urls(id),
    status_code INT,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP NOT NULL
);
CREATE INDEX idx_urls_check_lastCheck
    ON url_checks (url_id, created_at DESC, status_code);

