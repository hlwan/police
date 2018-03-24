
create table account(
  id text primary key not null,
  password TEXT ,
  name TEXT ,
  phone TEXT,
  create_time datetime,
  update_time datetime
);

create table account_visit(
  id text primary key not null,
  account_id TEXT not null ,
  url TEXT ,
  status INT ,
  create_time datetime,
  response TEXT
);