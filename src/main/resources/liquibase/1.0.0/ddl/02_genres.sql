--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/genre_seq
--rollback drop sequence events.genre_seq;
create sequence events.genre_seq;

--changeset eekovtun:1.0.0/ddl/genres
--rollback drop table events.genres;
create table events.genres
(
    id   bigint default nextval('events.genre_seq') not null
        constraint genres_pk primary key,
    name varchar(255)
);

create unique index genres_name_uindex
    on events.genres (name);

comment on table events.genres is 'Table stores genres as enums';
comment on column events.genres.id is 'Primary key of the table';
comment on column events.genres.name is 'Genre name';