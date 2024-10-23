CREATE TABLE task(
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    assigned_user_id BIGINT,
    status VARCHAR(255),
    completed_at TIMESTAMP
);