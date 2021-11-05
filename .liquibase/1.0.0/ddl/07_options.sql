--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/option_seq
--rollback drop sequence events.option_seq;
create sequence events.option_seq;

--changeset eekovtun:1.0.0/ddl/options
--rollback drop table events.options;
create table events.options
(
    id       bigint       default nextval('events.option_seq') not null
        constraint options_pk primary key,
    event_id bigint                                            not null
        constraint options_events_id_fk
            references events.events,
    name     varchar(255),
    created  timestamp(0) default now()                        not null,
    updated  timestamp(0) default now()                        not null,
    active   boolean      default true
);

comment on table events.options is 'Table stores event options';
comment on column events.options.id is 'Primary key of the table';
comment on column events.options.event_id is 'Reference on address id';
comment on column events.options.name is 'Option name';
comment on column events.options.created is 'Time of created';
comment on column events.options.updated is 'Time of the last updated';
comment on column events.options.active is 'A soft deleted flag: true - active, false - deleted';