DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks(
    id IDENTITY,
    title VARCHAR(100),
    description VARCHAR(1024),
    project VARCHAR(100),
    prio INT,
    created_at TIMESTAMP,
    edited_at TIMESTAMP
);

CREATE TABLE attachments(
    id IDENTITY,
    filename VARCHAR(100),
    type VARCHAR(1),
    ownerId NUMERIC,
    created_at TIMESTAMP,
    tasks NUMERIC,
    FOREIGN KEY (tasks) REFERENCES tasks(id)
);

CREATE TABLE comments(
    id IDENTITY,
    text VARCHAR(1024),
    type VARCHAR(1),
    ownerId NUMERIC,
    created_at TIMESTAMP,
    edited_at TIMESTAMP,
    attachments NUMERIC,
    FOREIGN KEY (attachments) REFERENCES attachments(id)
);
