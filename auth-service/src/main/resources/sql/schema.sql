create table if not exists app_user(
    id serial primary key,
    username varchar(255),
    password varchar(255)
);