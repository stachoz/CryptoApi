drop table if exists coin;
drop table if exists transaction;
drop table if exists status;

create table coin(
    id bigint auto_increment primary key,
    coin_name varchar(40) unique not null,
    symbol varchar(10) unique not null
);

create table transaction(
    id bigint auto_increment primary key,
    amount decimal not null,
    prize decimal not null,
    time_added timestamp,
    type varchar(10) not null
);

create table status(
    id bigint auto_increment primary key,
    current_amount decimal not null,
    current_profit decimal not null,
    coin_id bigint not null,
    foreign key (coin_id) references coin(id)
);

alter table coin add transaction_id bigint not null references transaction(id);