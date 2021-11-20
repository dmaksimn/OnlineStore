create table cart
(
    id         bigserial
        constraint cart_pk
            primary key,
    user_id    bigint
        constraint fkg5uhi8vpsuy0lgloxk2h4w5o6
            references users,
    product_id bigint,
    count      integer,
    "create"   timestamp,
    update     timestamp,
    status     integer
);

alter table cart
    owner to postgres;

create unique index cart_id_uindex
    on cart (id);
create table order_main
(
    id                  bigserial
        constraint order_main_pk
            primary key,
    user_id             bigint
        constraint order_main_users_id_fk
            references users,
    product_in_order_id bigint
        constraint order_main_product_in_order_id_fk
            references product_in_order,
    status              integer,
    "create"            timestamp,
    update              timestamp,
    order_amount        numeric,
    address_delivery    varchar default 'NY,14301 Main Street'::character varying,
    client_email        varchar
);

alter table order_main
    owner to postgres;

create unique index order_main_id_uindex
    on order_main (id);

create table product_category
(
    id       bigserial
        constraint product_category_pk
            primary key,
    name     varchar default 'pants'::character varying,
    type     integer
        constraint uk_6kq6iveuim6wd90cxo5bksumw
            unique,
    "create" timestamp,
    update   timestamp
);

alter table product_category
    owner to postgres;

create unique index product_category_id_uindex
    on product_category (id);

INSERT INTO public.product_category (id, name, type, "create", update) VALUES (1, 'Pants', 0, '2021-11-18 22:19:15.000000', '2021-11-18 22:19:18.000000');
INSERT INTO public.product_category (id, name, type, "create", update) VALUES (2, 'Skirt', 1, '2021-11-18 23:00:06.000000', '2021-11-18 23:00:11.000000');
INSERT INTO public.product_category (id, name, type, "create", update) VALUES (3, 'Bra', 2, '2021-11-18 23:00:35.000000', '2021-11-18 23:00:33.000000');
INSERT INTO public.product_category (id, name, type, "create", update) VALUES (4, 'T-Short', 3, '2021-11-18 23:00:57.000000', '2021-11-18 23:01:05.000000');

create table product_in_order
(
    id            bigserial
        constraint product_in_order_pk
            primary key,
    user_id       bigint
        constraint product_in_order_product_info_id_fk
            references product_info,
    product_id    bigint
        constraint product_in_order_product_info_id_fk_2
            references product_info,
    form_pay      integer default 0,
    status        integer default 0,
    count         integer,
    order_main_id bigint
        constraint product_in_order_order_main_id_fk
            references order_main,
    cart_id       bigint
        constraint product_in_order_cart_id_fk
            references cart,
    price         numeric
);

alter table product_in_order
    owner to postgres;

create unique index product_in_order_id_uindex
    on product_in_order (id);

create table product_info
(
    id          bigserial
        constraint product_info_pk
            primary key,
    name        varchar,
    category_id integer,
    description varchar,
    icon        varchar,
    price       numeric,
    stock       integer,
    status      integer,
    "create"    timestamp,
    update      timestamp
);

alter table product_info
    owner to postgres;

create unique index product_info_id_uindex
    on product_info (id);

INSERT INTO public.product_info (id, name, category_id, description, icon, price, stock, status, "create", update) VALUES (1, 'pants', 0, 'pants', 'https://image.shutterstock.com/image-photo/trousers-600w-51881968.jpg', 10, 10, 0, '2021-11-18 22:13:37.000000', '2021-11-18 22:13:40.000000');
INSERT INTO public.product_info (id, name, category_id, description, icon, price, stock, status, "create", update) VALUES (3, 'bra', 2, 'bra', 'https://sc04.alicdn.com/kf/Hc756b55803424253a1e93e43384d40a4i.jpg', 25, 25, 1, '2021-11-18 23:09:26.000000', '2021-11-18 23:09:31.000000');
INSERT INTO public.product_info (id, name, category_id, description, icon, price, stock, status, "create", update) VALUES (4, 't-short', 3, 't-short', 'https://static.nike.com/a/images/t_PDP_864_v1/f_au…26d56ec90681/sportswear-womens-t-shirt-xgRNkg.png', 5, 100, 0, '2021-11-18 23:10:04.000000', '2021-11-18 23:10:06.000000');
INSERT INTO public.product_info (id, name, category_id, description, icon, price, stock, status, "create", update) VALUES (2, 'skirt', 1, 'skirt', 'https://m.media-amazon.com/images/I/81W3PX-CKSL._AC_UX522_.jpg', 15, 15, 0, '2021-11-18 23:08:56.000000', '2021-11-18 23:09:00.000000');

create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    name     varchar,
    password varchar,
    email    varchar
        constraint uk_sx468g52bpetvlad2j9y0lptc
            unique,
    phone    integer,
    address  varchar,
    role     varchar,
    active   boolean
);

alter table users
    owner to postgres;

create unique index users_id_uindex
    on users (id);

INSERT INTO public.users (id, name, password, email, phone, address, role, active) VALUES (1, 'Manager1', '123', 'man@store.com', 123, 'Minsk', 'admin', true);

create table user_cabinet
(
    id            bigserial
        constraint user_cabinet_pk
            primary key,
    user_id       bigint,
    order_main_id bigint
);

alter table user_cabinet
    owner to postgres;

create unique index user_cabinet_id_uindex
    on user_cabinet (id);

