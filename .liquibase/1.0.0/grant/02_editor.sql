--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/editor context:!local
--rollback revoke all on schema users from editor;
grant usage on schema events to editor;
grant select, insert, update, delete on all tables in schema events to editor;
grant usage, select on all sequences in schema events to editor;