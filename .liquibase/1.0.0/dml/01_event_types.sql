--liquibase formatted sql

--changeset eekovtun:1.0.0/dml/event_types
--rollback delete from events.event_types where id != null;
insert into events.event_types (name)
values ('Концерт');