insert into ref_goods (id, name, price) values (1, 'товар 1', 11);
insert into ref_goods (id, name, price) values (2, 'товар 2', 1.2);
insert into ref_goods (id, name, price) values (3, 'товар 3', 1.3);
insert into ref_goods (id, name, price) values (4, 'товар 4', 4);

insert into t_order(id, customer, num, order_time) values (1, 'vasya@mail.ru', 01, '2020-07-10 21:00:00');
insert into t_order(id, customer, num, order_time) values (2, 'john.doe@mail.com', 2, '2020-07-10 21:01:00');

insert into tp_order_items(id, price, quantity, sum, goods_id) values (1, 11, 2, 22, 4);
insert into tp_order_items(id, price, quantity, sum, goods_id) values (2, 1.2, 1, 1.2, 4);
insert into tp_order_items(id, price, quantity, sum, goods_id) values (3, 1.3, 3, 3.9, 4);
insert into tp_order_items(id, price, quantity, sum, goods_id) values (4, 4, 4, 16, 4);

insert into tp_order_items(id, price, quantity, sum, goods_id) values (5, 100, 4, 400, 4);

insert into t_order_items(order_id, items_id) values (1, 1);
insert into t_order_items(order_id, items_id) values (1, 2);
insert into t_order_items(order_id, items_id) values (1, 3);
insert into t_order_items(order_id, items_id) values (1, 4);
insert into t_order_items(order_id, items_id) values (2, 5);
