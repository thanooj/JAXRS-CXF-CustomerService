create table employee.employee (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(30) not null, location varchar(30) not null);
insert into employee.employee (name, location) values ('ram','ayodhya');
insert into employee.employee (name, location) values ('seeta','midhila');
insert into employee.employee (name, location) values ('lakshman','ayodhya');
insert into employee.employee (name, location) values ('hanuma','kiskindha');