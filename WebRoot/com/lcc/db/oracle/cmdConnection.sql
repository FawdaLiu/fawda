--win下用sqlplus连接oracle
--第一步、
sqlplus
--输入用户名、密码
--报错：ORA-12560: TNS: 协议适配器错误
--在连接前在注册表regedit里找到ORCLE_SID看下是否是想要连接的sid
--如果不是，a、set oracle_sid=orcl；b、配置环境变量；
--sys用户以DBA登陆
conn sys/ as sysdba;

sqlplus /nolog
--运行sqlplus命令，进入sqlplus环境。其中/nolog是不登陆到数据库服务器的意思，如果没有/nolog参数，sqlplus会提示输入用户名密码
--第二步、
connect / as sysdba
--以系统管理员（sysdba）身份连接数据库，典型的操作系统认证，不需要listener进程
connect sys/oracle 
--这种连接方式只能连接本机数据库，同样不需要listener进程
connect sys@service_name as sysdba
--对数据库进行管理操作，service_name是配置的客户tnsname服务名
--这种方式需要listener进程处于可用状态，最普遍的通过网络连接

--第三步、
startup 
--如果没有启动的话，通过该命令可以启动数据库

--查看服务器端listener进程的状态
lsnrctl status
--tnsping 查看客户端sqlnet.ora和tnsname.ora文件的配置正确与否，及对应的服务器的listener进程的状态
tnsping
--查看linstance是否已经启动
sql〉show sga

--查询数据库服务与实例名称
select '数据库名称' AS 参数名称, name AS 参数值, 1 AS 排序 FROM v$database UNION
select '实例名称' AS 参数名称, instance_name AS 参数值, 2 AS 排序 FROM v$instance UNION
select '服务名称' AS 参数名称, value AS 参数值, 3 AS 排序 FROM v$parameter where name = 'service_names'