--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/viewer context:!local
--rollback revoke all on schema events from viewer;
grant usage on schema events to viewer;
grant select, insert, update on all tables in schema events to viewer;
grant usage, select on all sequences in schema events to viewer;