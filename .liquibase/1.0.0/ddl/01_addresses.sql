--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/address_seq
--rollback drop sequence events.address_seq;
create sequence events.address_seq;

--changeset eekovtun:1.0.0/ddl/addresses
--rollback drop table events.addresses;
create table events.addresses
(
    id      bigint       default nextval('events.address_seq') not null
        constraint addresses_pk primary key,
    address text                                               not null,
    created timestamp(0) default now()                         not null,
    updated timestamp(0) default now()                         not null,
    active  boolean      default true
);

comment on table events.addresses is 'Table stores event addresses';
comment on column events.addresses.id is 'Primary key of the table';
comment on column events.addresses.address is 'Full address of event';
comment on column events.addresses.created is 'Time of created';
comment on column events.addresses.updated is 'Time of the last updated';
comment on column events.addresses.active is 'A soft deleted flag: true - active, false - deleted';