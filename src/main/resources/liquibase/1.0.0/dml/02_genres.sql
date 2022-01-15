--liquibase formatted sql

--changeset eekovtun:1.0.0/dml/genres
--rollback delete from events.genres where id != null;
insert into events.genres (name) values ('Авторская песня');
insert into events.genres (name) values ('Блэк-Металл');
insert into events.genres (name) values ('Блюз');
insert into events.genres (name) values ('Грайндкор');
insert into events.genres (name) values ('Джаз');
insert into events.genres (name) values ('Индастриал');
insert into events.genres (name) values ('Кантри');
insert into events.genres (name) values ('Классическая музыка');
insert into events.genres (name) values ('Латинос');
insert into events.genres (name) values ('Народная музыка');
insert into events.genres (name) values ('Нью-эйдж');
insert into events.genres (name) values ('Поп');
insert into events.genres (name) values ('Поп-Рок');
insert into events.genres (name) values ('Ритм-н-Блюз');
insert into events.genres (name) values ('Рок');
insert into events.genres (name) values ('Рок-н-Ролл');
insert into events.genres (name) values ('Романс');
insert into events.genres (name) values ('Рэп');
insert into events.genres (name) values ('Синти-Поп');
insert into events.genres (name) values ('Соул');
insert into events.genres (name) values ('Техно');
insert into events.genres (name) values ('Трэш-Металл');
insert into events.genres (name) values ('Фолк');
insert into events.genres (name) values ('Хэви-Металл');
insert into events.genres (name) values ('Шансон');
insert into events.genres (name) values ('Электро');
insert into events.genres (name) values ('Электронная музыка');
insert into events.genres (name) values ('Эмбиент');
insert into events.genres (name) values ('Этериал');