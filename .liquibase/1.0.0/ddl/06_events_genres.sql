--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/event_genre_seq
--rollback drop sequence events.event_genre_seq;
create sequence events.event_genre_seq;

--changeset eekovtun:1.0.0/ddl/events_genres
--rollback drop table events.events_genres;
create table events.events_genres
(
    id       bigint default nextval('events.event_genre_seq') not null
        constraint events_genres_pk primary key,
    event_id bigint                                           not null
        constraint events_genres_event_id_fk
            references events.events,
    genre_id bigint                                           not null
        constraint events_genres_genre_id_fk
            references events.genres
);

create unique index events_genres_ids_fk_uniq
    on events.events_genres (event_id, genre_id);

comment on table events.events_genres is 'A table stores links events and genres';
comment on column events.events_genres.id is 'A primary key of the table';
comment on column events.events_genres.event_id is 'Reference on a event id';
comment on column events.events_genres.genre_id is 'Reference on a genre id';