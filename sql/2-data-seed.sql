-- DATA SEED

-- Customers
insert into customers(id, first_name, last_name, ssn, created, last_updated)
values
    (1, 'Daniel', 'Fellden', '8505243241', current_timestamp(), current_timestamp()),
    (2, 'Alex', 'Brun', '5052412416', current_timestamp(), current_timestamp()),
    (3, 'Ceasar', 'Olle', '8505241241', current_timestamp(), current_timestamp());

-- Förberett order utan några items i sig
insert into orders(id, created, last_updated, customer_id)
values
    (1, current_timestamp(),  current_timestamp(), 1),
    (2, current_timestamp(),  current_timestamp(), 2),
    (3, current_timestamp(),  current_timestamp(), 2),
    (4, current_timestamp(),  current_timestamp(), 3);

-- Items
insert into items(id, name, price, balance, created, last_updated)
values
    (1, 'banana', 20, 155, current_timestamp(), current_timestamp()),
    (2, 'apple', 25, 55, current_timestamp(), current_timestamp()),
    (3, 'orange', 10, 10, current_timestamp(), current_timestamp()),
    (4, 'pear', 15, 10, current_timestamp(), current_timestamp()),
    (5, 'macbook pro', 5000, 23, current_timestamp(), current_timestamp()),
    (6, 'Katalysator, universal', 629, 23, current_timestamp(), current_timestamp());
