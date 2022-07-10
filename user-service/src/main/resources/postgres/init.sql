create table users (
    id serial primary key,
    name varchar(50),
    balance int
);

create table user_transaction(
    id serial primary key,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    constraint fk_user_id
    foreign key (user_id)
    references users (id)
    on delete cascade
);

insert into users
    (name, balance)
    values
    ('sam', 1000),
    ('mike', 1200),
    ('jake', 800),
    ('marshal', 2000);