--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/ticket_seq
--rollback drop sequence events.ticket_seq;
create sequence events.ticket_seq;

--changeset eekovtun:1.0.0/ddl/tickets
--rollback drop table events.tickets;
create table events.tickets
(
    id       bigint       default nextval('events.ticket_seq') not null
        constraint tickets_pk primary key,
    event_id bigint
        constraint tickets_events_id_fk
            references events.events,
    quantity int,
    cost     decimal(7, 4)                                     not null,
    price    decimal(7, 4)                                     not null,
    created  timestamp(0) default now()                        not null,
    updated  timestamp(0) default now()                        not null,
    active   boolean      default true
);

comment on table events.tickets is 'Table stores event tickets';
comment on column events.tickets.id is 'Primary key of the table';
comment on column events.tickets.event_id is 'Reference on event id';
comment on column events.tickets.quantity is 'Count of tickets';
comment on column events.tickets.cost is 'Value without tax';
comment on column events.tickets.price is 'Value with tax';
comment on column events.tickets.created is 'Time of created';
comment on column events.tickets.updated is 'Time of the last updated';
comment on column events.tickets.active is 'A soft deleted flag: true - active, false - deleted';