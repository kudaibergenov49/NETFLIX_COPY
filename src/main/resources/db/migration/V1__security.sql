create table roles
(
    id bigint auto_increment primary key,
    created datetime     null,
    name    varchar(255) null,
    status  varchar(255) null,
    updated datetime     null
)
    engine = MyISAM;

create table user_roles
(
    user_id  bigint not null,
    roles_id bigint not null
)
    engine = MyISAM;

create index FKdbv8tdyltxa1qjmfnj9oboxse
    on user_roles (roles_id);

create index FKhfh9dx7w3ubf1co1vdev94g3f
    on user_roles (user_id);

create table users
(
    id bigint auto_increment primary key,
    created    datetime     null,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null,
    status     varchar(255) null,
    updated    datetime     null,
    username   varchar(255) null
)
    engine = MyISAM;

