-- DATA SEED

insert into customers(id, first_name, last_name, ssn, created, last_updated)
values
(10001, 'Daniel', 'Fellden', '8505243241', current_timestamp(), current_timestamp()),
(10002, 'Alex', 'Brun', '5052412416', current_timestamp(), current_timestamp()),
(10003, 'Ceasar', 'Olle', '8505241241', current_timestamp(), current_timestamp());


insert into orders(id, created, last_updated, customer_id)
values
(10001, current_timestamp(),  current_timestamp(), 10001);

insert into items(id, name, price, balance, created, last_updated)
values
(10001, 'banana', 20, 155, current_timestamp(), current_timestamp()),
(10002, 'apple', 25, 55, current_timestamp(), current_timestamp()),
(10003, 'orange', 10, 10, current_timestamp(), current_timestamp()),
(10004, 'pear', 15, 10, current_timestamp(), current_timestamp()),
(10005, 'macbook pro', 5000, 23, current_timestamp(), current_timestamp());