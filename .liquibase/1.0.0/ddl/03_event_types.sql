--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/event_type_seq
--rollback drop sequence events.event_type_seq;
create sequence events.event_type_seq;

--changeset eekovtun:1.0.0/ddl/event_types
--rollback drop table events.event_types;
create table events.event_types
(
    id   bigint default nextval('events.event_type_seq') not null
        constraint event_types_pk primary key,
    name varchar(255)
);

comment on table events.event_types is 'Table stores event types as enums';
comment on column events.event_types.id is 'Primary key of the table';
comment on column events.event_types.name is 'Type name';