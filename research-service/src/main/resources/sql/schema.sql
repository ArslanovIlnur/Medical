create table if not exists research(
    id serial primary key,
    patient_id integer,
    date Date,
    initials varchar(3),
    research_type varchar(255),
    research_zone varchar(255)
);