create table customer (
  id             serial PRIMARY KEY,
  first_name     varchar(150),
  last_name      varchar(150),
  email          varchar(255),
  time_created   timestamp,
  time_modified  timestamp,
  version        integer
);