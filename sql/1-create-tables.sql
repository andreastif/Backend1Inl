create table customers (
                           id bigint not null,
                           created date,
                           first_name varchar(255),
                           last_name varchar(255),
                           last_updated date,
                           ssn varchar(12),
                           primary key (id)
) engine=InnoDB;

create table items (
                       id bigint not null,
                       balance bigint,
                       created date,
                       img_data LONGBLOB,
                       last_updated date,
                       name varchar(255),
                       price bigint check (price>=1),
                       primary key (id)
) engine=InnoDB;

create table order_item (
                            id bigint not null,
                            quantity integer not null,
                            item_id bigint,
                            order_id bigint,
                            primary key (id)
) engine=InnoDB;


create table orders (
                        id bigint not null,
                        created date,
                        last_updated date,
                        customer_id bigint not null,
                        primary key (id)
) engine=InnoDB;

create sequence customers_seq start with 4 increment by 1;
create sequence items_seq start with 7 increment by 1;
create sequence order_item_seq start with 1 increment by 1;
create sequence orders_seq start with 5 increment by 1;

alter table order_item
    add constraint FKo5d8io03ue2y89j3wbnju0let
        foreign key (item_id)
            references items (id);

alter table order_item
    add constraint FKt4dc2r9nbvbujrljv3e23iibt
        foreign key (order_id)
            references orders (id);


alter table orders
    add constraint FKpxtb8awmi0dk6smoh2vp1litg
        foreign key (customer_id)
            references customers (id)
            on delete cascade;