create table user
(
    id            bigint auto_increment
        primary key,
    username      varchar(255)         not null,
    password      varchar(2047)        not null,
    is_admin      tinyint(1)           not null,
    phone_number  varchar(11)          not null,
    last_login    datetime             null,
    last_login_ip varchar(32)          null,
    register_time datetime             not null,
    register_ip   varchar(32)          not null,
    salt          varchar(63)          not null,
    is_disabled   tinyint(1) default 0 not null,
    is_deleted    tinyint(1) default 0 not null,
    constraint user_pk
        unique (username, phone_number)
);

