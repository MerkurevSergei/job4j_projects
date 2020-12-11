CREATE TABLE Item (
    id SERIAL PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    created TIMESTAMP NOT NULL,
    done BOOLEAN,
    user_id INTEGER REFERENCES "user" (id)
);