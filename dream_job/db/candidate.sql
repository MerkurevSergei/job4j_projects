CREATE TABLE candidate
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    photoId TEXT,
    cityId INTEGER REFERENCES city
);