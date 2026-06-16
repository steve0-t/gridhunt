drop table if exists comment;
create table comment (
    player varchar(64) not null,
    game varchar(32) not null,
    comment varchar(350) not null,
    commented_on timestamp not null
);


drop table if exists score;
create table score (
    player varchar(64) not null,
    game varchar(32) not null,
    points integer not null,
    played_on timestamp not null
);


drop table if exists rating;
create table rating (
    player varchar(64) not null,
    game varchar(32) not null,
    rating integer not null check(rating >= 1 and rating <= 5),
    rated_on timestamp not null
);

drop table if exists users;
create table users (
    username varchar(64) not null,
    password varchar(128) not null
);
