--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/event_seq
--rollback drop sequence events.event_seq;
create sequence events.event_seq;

--changeset eekovtun:1.0.0/ddl/events
--rollback drop table events.events;
create table events.events
(
    id               bigint        default nextval('events.event_seq') not null
        constraint events_pk primary key,
    address_id       bigint
        constraint events_addresses_id_fk
            references events.addresses,
    event_type_id    bigint
        constraint events_events_types_id_fk
            references events.event_types,
    external_user_id bigint                                            not null,
    name             varchar(255),
    description      text,
    start_time       time with time zone,
    start_date       date,
    total_amount     decimal(7, 4),
    current_amount   decimal(7, 4) default 0.0,
    over_date        timestamp(0) with time zone,
    draft            boolean       default true,
    created          timestamp(0)  default now()                       not null,
    updated          timestamp(0)  default now()                       not null,
    active           boolean       default true
);

comment on table events.events is 'Table stores events';
comment on column events.events.id is 'Primary key of the table';
comment on column events.events.address_id is 'Reference on address id';
comment on column events.events.address_id is 'User primary key of the user-handler service';
comment on column events.events.name is 'Event name';
comment on column events.events.description is 'Description of event';
comment on column events.events.start_time is 'Time of start event';
comment on column events.events.start_date is 'Date of start event';
comment on column events.events.total_amount is 'Total amount for the event';
comment on column events.events.current_amount is 'Current amount of fundraising';
comment on column events.events.over_date is 'Deadline of fundraising';
comment on column events.events.draft is 'Is it draft? If yes, then will be true and conversely';
comment on column events.events.created is 'Time of created';
comment on column events.events.updated is 'Time of the last updated';
comment on column events.events.active is 'A soft deleted flag: true - active, false - deleted';