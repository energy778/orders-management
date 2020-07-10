-- файл называем чётко по алгоритму: V + номер миграции + двойное подчеркивание + краткий комментарий без пробелов + расширение sql
-- также меняем свойство jpa.hibernate.ddl-auto на validate - теперь будет просто проверяться на соответствие тому, что мы имеем в БД. если будут расхождения, то приложение не запустится

create sequence if not exists hibernate_sequence start 1 increment 1;

create table if not exists ref_goods(
    id serial primary key,
    goods_name varchar(255) not null,
    price float not null
);

create table if not exists orders (
    id serial primary key,
    num int8 not null,
    order_time timestamp not null,
    customer varchar(255) not null
);

create table if not exists order_item (
    id serial primary key,
    order_id int8 not null,
    goods_id int8 not null,
    price float not null,
    g_count int8 not null,
    g_sum float not null
);

ALTER TABLE order_item
ADD CONSTRAINT item_order_key
UNIQUE (id, order_id, goods_id);

ALTER TABLE order_item
ADD CONSTRAINT order_key
FOREIGN KEY (order_id) REFERENCES orders(id);

ALTER TABLE order_item
ADD CONSTRAINT goods_key
FOREIGN KEY (goods_id) REFERENCES ref_goods(id);
