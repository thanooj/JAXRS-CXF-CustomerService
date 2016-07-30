--how to start DB server
C:\Apache\db-derby-10.4.1.3-bin\lib> java -jar derbyrun.jar server start


-- how to connect to a database, once after starting the network server
ij> connect 'jdbc:derby://localhost:1527/customer';


ij> show tables;
TABLE_SCHEM         |TABLE_NAME                    |REMARKS
------------------------------------------------------------------------
SYS                 |SYSALIASES                    |
SYS                 |SYSCHECKS                     |
SYS                 |SYSCOLPERMS                   |
SYS                 |SYSCOLUMNS                    |
SYS                 |SYSCONGLOMERATES              |
SYS                 |SYSCONSTRAINTS                |
SYS                 |SYSDEPENDS                    |
SYS                 |SYSFILES                      |
SYS                 |SYSFOREIGNKEYS                |
SYS                 |SYSKEYS                       |
SYS                 |SYSPERMS                      |
SYS                 |SYSROLES                      |
SYS                 |SYSROUTINEPERMS               |
SYS                 |SYSSCHEMAS                    |
SYS                 |SYSSEQUENCES                  |
SYS                 |SYSSTATEMENTS                 |
SYS                 |SYSSTATISTICS                 |
SYS                 |SYSTABLEPERMS                 |
SYS                 |SYSTABLES                     |
SYS                 |SYSTRIGGERS                   |
SYS                 |SYSUSERS                      |
SYS                 |SYSVIEWS                      |
SYSIBM              |SYSDUMMY1                     |

23 rows selected


drop table customer.customer;
create table customer.customer (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(30) not null, location varchar(30) not null);
insert into customer.customer (name, location) values ('ram','ayodhya');
insert into customer.customer (name, location) values ('seeta','midhila');
insert into customer.customer (name, location) values ('lakshman','ayodhya');
insert into customer.customer (name, location) values ('hanuma','kiskindha');


ij> select * from customer.customer;
ID         |NAME                          |LOCATION
-------------------------------------------------------------------------
1          |ram                           |ayodhya
2          |seeta                         |midhila
3          |lakshman                      |ayodhya

3 rows selected


ij>