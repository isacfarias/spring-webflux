create table users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name varchar(50),
    balance bigint,
    primary key (id)
);

create table user_transaction(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id bigint,
    amount bigint,
    transaction_date timestamp,
    foreign key (user_id) references users (id) on delete cascade
);

insert into users
    (name, balance)
    values
    ('sam', 1000),
    ('mike', 1200),
    ('jake', 800),
    ('marshal', 2000);