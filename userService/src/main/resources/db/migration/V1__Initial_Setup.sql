CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) ,
    email VARCHAR(255) NOT NULL,
    age INT,
    qualification VARCHAR(255)
);