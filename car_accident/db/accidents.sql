CREATE TABLE accidents (
  id serial primary key,
  name text,
  text text,
  address text,
  type_id integer references accident_types
);