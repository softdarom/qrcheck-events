--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/events context:!local
--rollback revoke all on schema events from events;
grant connect on database qrcheck to events;
grant usage on schema events to events;
grant select, insert, update, delete on all tables in schema events to events;
grant usage, select on all sequences in schema events to events;