--liquibase formatted sql

--changeset eekovtun:1.0.0/dml/genres
--rollback delete from events.genres where id != null;
insert into events.genres (genre) values ('ELECTRONICA');