#连接数据库
mysql -u root -p

#显示当前数据库
show databases;

#创建数据库
create database xxx;

#Duplicate entry '0.000000000000000000000000000000' for key 'PRIMARY'

####### DLL #####
#设置自增
#已存在表添加自增
alter table CORE_LOG MODIFY CRLOG_UNID int UNSIGNED AUTO_INCREMENT;
alter table CORE_LOG change id CRLOG_UNID int(11) not null AUTO_INCREMENT;

#删除
Alter table tb change id id int(10);#删除自增长
Alter table tb drop primary key;#删除主建

#添加表，并加自增主键
create table table1(id int auto_increment primary key,...)

#创建表格后添加：自增字段，一定要设置为primary key.
alter table table1 add id int auto_increment primary key
#设置从哪里开始
alter table users AUTO_INCREMENT=10000;

#查询数据库中所有表的主键及数量：
SELECT
	t.TABLE_NAME,
	t.CONSTRAINT_TYPE,
	c.COLUMN_NAME,
	c.ORDINAL_POSITION
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c,
	information_schema.TABLES AS ts
WHERE
	t.TABLE_NAME = c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY';


#查询数据库中所有表的自增主键：
SELECT
	t.TABLE_NAME,
	c.COLUMN_NAME,
	ts.AUTO_INCREMENT
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	information_schema.TABLES AS ts,
	information_schema.KEY_COLUMN_USAGE AS c
WHERE
	t.TABLE_NAME = ts.TABLE_NAME
	AND ts.TABLE_NAME	= c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY'
	ORDER BY ts.`AUTO_INCREMENT` DESC;
#批量重新使主键自增
SELECT
	concat_ws(' ', 'alter table', t.TABLE_NAME, 'MODIFY', c.COLUMN_NAME, 'int UNSIGNED AUTO_INCREMENT;')
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	information_schema.TABLES AS ts,
	information_schema.KEY_COLUMN_USAGE AS c
WHERE
	t.TABLE_NAME = ts.TABLE_NAME
	AND ts.TABLE_NAME	= c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY'
	ORDER BY ts.`AUTO_INCREMENT` DESC;


#显示当前服务器版本
select version();
#显示当前日期时间
select now();
#显示当前用户
select user();
#显示系统当前的数据库
show databases;

#启动
net start mysql
#停止
net stop mysql
# 创建用户
 create user 'test'@'localhost' identified by '1234';

 #授予用户test通过外网IP对数据库“testdb”的全部权限
grant all privileges on 'testdb'.* to 'test'@'%' identified by '1234';
#授予用户“test”通过外网IP对于该数据库“testdb”中表的创建、修改、删除权限,以及表数据的增删查改权限
grant create,alter,drop,select,insert,update,delete on testdb.* to test@'%';
#刷新权限
flush privileges;

#-产看授权普通用户
select host,user,grant_priv,Super_priv from mysql.user where User = 'risen_hzrisen_sms_server';

#方法1，密码实时更新；修改用户“test”的密码为“1122”
set password for test =password('1122');
#方法2，需要刷新；修改用户“test”的密码为“1234”
update  mysql.user set  password=password('1234')  where user='test'
#刷新
flush privileges;


#Duplicate entry '0.000000000000000000000000000000' for key 'PRIMARY'
#设置自增
#已存在表添加自增
alter table CORE_LOG MODIFY CRLOG_UNID int UNSIGNED AUTO_INCREMENT;
alter table CORE_LOG change id CRLOG_UNID int(11) not null AUTO_INCREMENT;

#删除
Alter table tb change id id int(10);//删除自增长
Alter table tb drop primary key;//删除主建

#添加表，并加自增主键
create table table1(id int auto_increment primary key,...)

#创建表格后添加：自增字段，一定要设置为primary key.
alter table table1 add id int auto_increment primary key
#设置从哪里开始
alter table users AUTO_INCREMENT=10000;

#查询数据库中所有表的主键及数量：
SELECT
	t.TABLE_NAME,
	t.CONSTRAINT_TYPE,
	c.COLUMN_NAME,
	c.ORDINAL_POSITION
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c,
	information_schema.TABLES AS ts
WHERE
	t.TABLE_NAME = c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY';


#查询数据库中所有表的自增主键：
SELECT
	t.TABLE_NAME,
	c.COLUMN_NAME,
	ts.AUTO_INCREMENT
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	information_schema.TABLES AS ts,
	information_schema.KEY_COLUMN_USAGE AS c
WHERE
	t.TABLE_NAME = ts.TABLE_NAME
	AND ts.TABLE_NAME	= c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY'
	ORDER BY ts.`AUTO_INCREMENT` DESC;
#批量重新使主键自增
SELECT
	concat_ws(' ', 'alter table', t.TABLE_NAME, 'MODIFY', c.COLUMN_NAME, 'int UNSIGNED AUTO_INCREMENT;')
FROM
	INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,
	information_schema.TABLES AS ts,
	information_schema.KEY_COLUMN_USAGE AS c
WHERE
	t.TABLE_NAME = ts.TABLE_NAME
	AND ts.TABLE_NAME	= c.TABLE_NAME
	AND t.TABLE_SCHEMA = 'risenpt'
	AND t.CONSTRAINT_TYPE = 'PRIMARY KEY'
	ORDER BY ts.`AUTO_INCREMENT` DESC;


#查看数据库锁
show processlist;
#解锁
kill [pid];

#将一个表的数据copy到另一个表
#1.如果a表和b表结构相同。

insert into b select * from a;
#2.如果a表和b表的结构不相同。
insert into b(col1, col2, col3, ...) select a.col1, a.col2, a.col3, ... from a where ...;
#3.如果b表不存在。
select * into b from a;
select a.col1, a.col2, c.col3, ... into b from a;


#备份
#指定库
mysqldump -h127.0.0.1 -uroot -p #lock-tables #events #triggers #routines #flush-logs #master-data=2 #databases db1 db2 db3 > ./db.sql
#整库
mysqldump -uroot -p -hlocalhost #lock-all-tables #all-databases > /home/db.sql

#定时备份
time=_`date +%Y%m%d_%H%M%S`
db1_name=mysql_all
mysqldump #defaults-file=/home/soft/database/mysql/.my.cnf  -A | gzip > $backupdir/$db1_name$time.sql.gz
find $backupdir -name "*.sql.gz" -type f -mtime +3 -exec rm {} \; > /dev/null 2>&1

#还原
#mysqldump恢复指定库
mysqldump -u用户名 -p密码 -h主机 数据库 < 路径
#案例：
mysql -uroot -p db1 < /home/db.sql
#恢复所有库：
mysqldump -uroot -p -h 127.0.0.1 #all-databases < /home/db.sql

source xxx.sql
