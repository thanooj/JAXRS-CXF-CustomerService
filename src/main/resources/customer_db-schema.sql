create table customer.customer (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(30) not null, location varchar(30) not null);
insert into customer.customer (name, location) values ('ram','ayodhya');
insert into customer.customer (name, location) values ('seeta','midhila');
insert into customer.customer (name, location) values ('lakshman','ayodhya');
insert into customer.customer (name, location) values ('hanuma','kiskindha');