DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks(
    id IDENTITY,
    title VARCHAR(100),
    description VARCHAR(1024),
    project VARCHAR(100),
    prio INT,
    created_at TIMESTAMP,
    edited_at TIMESTAMP
)

