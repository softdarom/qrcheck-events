--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/image_seq
--rollback drop sequence events.image_seq;
create sequence events.image_seq;

--changeset eekovtun:1.0.0/ddl/images
--rollback drop table events.images;
create table events.images
(
    id                bigint       default nextval('events.image_seq') not null
        constraint images_pk primary key,
    event_id          bigint
        constraint images_events_id_fk
            references events.events                                   not null,
    external_image_id bigint                                           not null,
    cover             boolean      default false,
    created           timestamp(0) default now()                       not null,
    updated           timestamp(0) default now()                       not null,
    active            boolean      default true
);

comment on table events.images is 'Table stores images';
comment on column events.images.id is 'Primary key of the table';
comment on column events.images.event_id is 'Reference on event id';
comment on column events.images.external_image_id is 'Image primary key of an external content service';
comment on column events.images.cover is 'Is cover? If yes, then will be true and conversely';
comment on column events.images.created is 'Time of created';
comment on column events.images.updated is 'Time of the last updated';
comment on column events.images.active is 'A soft deleted flag: true - active, false - deleted';

--changeset eekovtun:1.0.0/ddl/images_audit context:!local
--rollback drop trigger images_audit on events.images;
create trigger images_audit
    after insert or update or delete
    on events.images
    for each row
execute procedure audit.audit_func();