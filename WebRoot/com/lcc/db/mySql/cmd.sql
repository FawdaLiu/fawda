--连接数据库
mysql -u root -p

--显示当前数据库
show databases;

--创建数据库
create database xxx;


--Duplicate entry '0.000000000000000000000000000000' for key 'PRIMARY'
--设置自增
--已存在表添加自增
alter table CORE_LOG MODIFY CRLOG_UNID int UNSIGNED AUTO_INCREMENT;
alter table CORE_LOG change id CRLOG_UNID int(11) not null AUTO_INCREMENT;

--删除
Alter table tb change id id int(10);//删除自增长
Alter table tb drop primary key;//删除主建

--添加表，并加自增主键
create table table1(id int auto_increment primary key,...)

--创建表格后添加：自增字段，一定要设置为primary key.
alter table table1 add id int auto_increment primary key 
--设置从哪里开始
alter table users AUTO_INCREMENT=10000;

--查询数据库中所有表的主键及数量：
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
	
	
--查询数据库中所有表的自增主键：
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
--批量重新使主键自增
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
	
	
--显示当前服务器版本
select version();
--显示当前日期时间
select now();
--显示当前用户
select user();
--显示系统当前的数据库
show databases;

--启动
net start mysql
--停止
net stop mysql


---授权普通用户
select host,user,grant_priv,Super_priv from mysql.user where User = 'risen_hzrisen_sms_server';

flush privileges ;


