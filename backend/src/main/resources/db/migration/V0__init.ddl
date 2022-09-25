create table oauth_user
(
    oauth2user_id varchar(255) not null
        primary key,
    created       datetime(6)  null,
    email         varchar(255) null,
    name          varchar(255) null,
    provider      int          null,
    user_id       bigint       null
);

create table user
(
    user_id  bigint auto_increment
        primary key,
    email    varchar(255) null,
    enabled  bit          not null,
    password varchar(255) null
);

create table user_authority
(
    user_id   bigint       not null,
    authority varchar(255) not null,
    primary key (user_id, authority),
    constraint user_id
        foreign key (user_id) references user (user_id)
);

