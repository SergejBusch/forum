create table posts (
                       id serial primary key,
                       name varchar(2000),
                       description text,
                       created timestamp without time zone not null default now()
);

create table users
(
    id serial
        constraint users_pk
            primary key,
    email varchar not null,
    name varchar not null,
    password varchar not null,
    authorities varchar
);

create unique index users_email_uindex
    on users (email);


