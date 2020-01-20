DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS tags_tasks;

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
    file_name VARCHAR(100),
    created_at TIMESTAMP,
    task_id NUMERIC,
    FOREIGN KEY (task_id) REFERENCES tasks(id)
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

CREATE TABLE tags
(
    id IDENTITY,
    name VARCHAR(64),
    created_at TIMESTAMP,
    edited_at TIMESTAMP
);

CREATE TABLE tags_tasks
(
    tag_id NUMERIC NOT NULL,
    task_id NUMERIC NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);