create table basket (
    id bigserial not null,
    user_id bigint unique,
    primary key (id));

create table basket_items (
    basket_id bigint not null,
    items_id bigint not null unique);

create table basket_item (
    quantity integer,
    basket_id bigint,
    id bigserial not null,
    product_id bigint,
    primary key (id));

create table image (
    id bigserial not null,
    name varchar(255) unique,
    path varchar(255),
    primary key (id));

create table order_tb (
    date_time timestamp(6),
    id bigserial not null,
    user_id bigint,
    primary key (id));

create table order_tb_items (
    items_id bigint not null unique,
    order_id bigint not null);

create table order_item (
    price integer,
    quantity integer,
    id bigserial not null,
    image_id bigint,
    order_id bigint,
    name varchar(255),
    primary key (id));

create table product (
    price integer,
    quantity integer,
    id bigserial not null,
    image_id bigint,
    category varchar(255) check (category in ('Fruits','Milk_products','Vegetables','Tea_Coffee','Dried_fruits','Greenery','all')),
    description varchar(255),
    name varchar(255) unique,
    primary key (id));

create table users (verified boolean,
                    basket_id bigint unique,
                    id bigserial not null,
                    address varchar(255),
                    comment varchar(255),
                    email varchar(255) unique,
                    orientation varchar(255),
                    password varchar(255),
                    phone_number varchar(255),
                    role varchar(255) check (role in ('ROLE_ADMIN','ROLE_USER')),
                    username varchar(255), uuid varchar(255),
                    verify_code varchar(255),
                    primary key (id));

create table users_orders (
    orders_id bigint not null unique,
    user_id bigint not null);




alter table if exists basket
    add constraint basket_user_fk
        foreign key (user_id) references users;


alter table if exists basket_items
    add constraint basket_item_items_fk
        foreign key (items_id) references basket_item;


alter table if exists basket_items
    add constraint basket_items_basket_fk
        foreign key (basket_id) references basket;


alter table if exists basket_item
    add constraint basket_item_basket_fk
        foreign key (basket_id) references basket;


alter table if exists order_tb
    add constraint order_tb_user_fk
        foreign key (user_id) references users;


alter table if exists order_tb_items
    add constraint order_tb_items_items_fk
        foreign key (items_id) references order_item;


alter table if exists order_tb_items
    add constraint order_tb_items_order_fk
        foreign key (order_id) references order_tb;


alter table if exists order_item
    add constraint order_item_order_fk
        foreign key (order_id) references order_tb;


alter table if exists product
    add constraint product_image_fk
        foreign key (image_id) references image;


alter table if exists users
    add constraint users_basket_fk
        foreign key (basket_id) references basket;


alter table if exists users_orders
    add constraint users_orders_order_fk
        foreign key (orders_id) references order_tb;


alter table if exists users_orders
    add constraint users_orders_user_fk
        foreign key (user_id) references users;