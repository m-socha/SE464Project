create table users (
  id integer primary key,
  email text unique not null,
  name text not null,
  created_at timestamp not null default now()
);

create table courses (
  id integer primary key,
  code text unique not null,
  title text not null,
  created_at timestamp not null default now()
);

create table notebooks (
  id integer primary key,
  user_id integer not null references users (id),
  course_id integer not null references courses (id),
  created_at timestamp not null default now()
);

create table notes (
  id integer primary key,
  notebook_id integer not null references notebooks (id),
  created_at timestamp not null default now()
);

create table comments (
  id integer primary key,
  user_id integer not null references users(id),
  content text,
  created_at timestamp not null default now()
);
