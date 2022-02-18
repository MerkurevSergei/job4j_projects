CREATE TABLE accidents_accident_rules (
  id serial primary key,
  accident_id integer references accidents,
  rule_id integer references accident_rules
);