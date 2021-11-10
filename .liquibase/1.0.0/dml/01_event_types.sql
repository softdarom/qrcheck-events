--liquibase formatted sql

--changeset eekovtun:1.0.0/dml/event_types
--rollback delete from events.event_types where id != null;
insert into events.event_types (name) values ('Опера');
insert into events.event_types (name) values ('Оперетта');
insert into events.event_types (name) values ('Мюзикл');
insert into events.event_types (name) values ('Спектакль');
insert into events.event_types (name) values ('Ток-Шоу');
insert into events.event_types (name) values ('Фестиваль');
insert into events.event_types (name) values ('Флешмоб');
insert into events.event_types (name) values ('Шоу');
insert into events.event_types (name) values ('Ярмарка');