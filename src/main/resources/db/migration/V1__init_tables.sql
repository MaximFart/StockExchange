drop table if exists securities, histories;

create table securities (
    id integer not null unique,
    secid varchar(50) not null primary key,
    regnumber varchar(40) not null,
    name varchar (200) not null,
    emitent_title text not null
);

create table histories (
    id bigserial not null primary key,
    secid varchar(50) not null,
    tradedate date not null,
    numtrades integer not null,
    open real not null,
    close real not null,
    foreign key (secid) references securities(secid)
);