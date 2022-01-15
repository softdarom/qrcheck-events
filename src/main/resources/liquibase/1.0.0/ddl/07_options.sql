--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/option_seq
--rollback drop sequence events.option_seq;
create sequence events.option_seq;

--changeset eekovtun:1.0.0/ddl/options
--rollback drop table events.options;
create table events.options
(
    id                 bigint       default nextval('events.option_seq') not null
        constraint options_pk primary key,
    event_id           bigint                                            not null
        constraint options_events_id_fk
            references events.events,
    name               varchar(255),
    quantity           int                                               not null,
    available_quantity int                                               not null
        constraint unsigned_available_quantity
            check ( available_quantity >= 0 ),
    cost               decimal(9, 2)                                     not null,
    price              decimal(9, 2)                                     not null,
    created            timestamp(0) default now()                        not null,
    updated            timestamp(0) default now()                        not null,
    active             boolean      default true
);

comment on table events.options is 'Table stores event options';
comment on column events.options.id is 'Primary key of the table';
comment on column events.options.event_id is 'Reference on address id';
comment on column events.options.name is 'Option name';
comment on column events.options.quantity is 'Count of options';
comment on column events.options.available_quantity is 'Count of options available';
comment on column events.options.cost is 'Value without tax';
comment on column events.options.price is 'Value with tax';
comment on column events.options.created is 'Time of created';
comment on column events.options.updated is 'Time of the last updated';
comment on column events.options.active is 'A soft deleted flag: true - active, false - deleted';

--changeset eekovtun:1.0.0/ddl/options_audit context:!local
--rollback drop trigger options_audit on events.options;
create trigger options_audit
    after insert or update or delete
    on events.options
    for each row
execute procedure audit.audit_func();