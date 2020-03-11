
--http://blog.itpub.net/

-- 1、系统会话信息
select '会话量' AS 指标项,
(select value FROM v$parameter where name = 'sessions')  AS 设定数量,
(select count(*) from v$session) AS 实际数量,
(select count(*) from v$session where status='ACTIVE') AS 活动数量
from DUAL
UNION
select '进程量' AS 指标项,
(select value FROM v$parameter where name = 'processes')  AS 设定数量,
(select count(*) from v$process) AS 实际数量,
(select count(*) from v$process) AS 活动数量
from DUAL

-- 2、数据库锁问题解决
-- 2.1查询数据库锁
SELECT object_name, machine, s.sid, s.serial# 
FROM gv$locked_object l, dba_objects o, gv$session s 
WHERE l.object_id = o.object_id 
AND l.session_id = s.sid;

select * from v$session t1, v$locked_object t2 where t1.sid = t2.SESSION_ID;
-- 2.2删除锁
alter system kill session '189, 7707'; 


-- 4、
--当前的连接数
select count(*) from v$process;
--数据库允许的最大连接数
select value from v$parameter where name = 'processes';
-- 修改最大连接数:
alter system set processes = 600 scope = spfile;

--查看当前有哪些用户正在使用数据
SELECT osuser, a.username,cpu_time/executions/1000000||'s', sql_fulltext,machine
from v$session a, v$sqlarea b
where a.sql_address =b.address order by cpu_time/executions desc;

-- 查询数据库自启动以来最大的并发数量
select * from v$license;
    
    
-- v$sqltext 存储的是完整的SQL,SQL被分割
--ADDRESS                                            RAW(4)    ---------
--HASH_VALUE                                         NUMBER   ---------  和 address 一起唯一标志一条sql
--COMMAND_TYPE                                       NUMBER
--PIECE                                              NUMBER   ----------  分片之后的顺序编号
--SQL_TEXT                                           VARCHAR2(64)   --------------  注意长度

--v$sqlarea   ---------  存储的SQL 和一些相关的信息，比如累计的执行次数，逻辑读，物理读等统计信息
--v$sql       ---------  存储的是具体的SQL 和执行计划相关信息，实际上，v$sqlarea 可以看做 v$sql 根据 sqltext 等 做了 group by 之后的信息
--v$sql_plan

--5、优化
--等待事件  读写数据量--- 网络带宽?
select * from (select event,total_waits,time_waited from v$system_event where wait_class!='Idle' order by 2 desc)  
where rownum<=5;

--数据库中的所有操作
select opname,target,sql_hash_value,count(*) from v$session_longops group by opname,target,sql_hash_value;  
    
--6、这个表这么大？ 全表扫描？ 什么业务？ 分区了？ 有索引？ 存在高水位？ 统计信息过期？
select * from user_tab_partitions  where table_name ='USER_TMP_POINT_IMEI_LABEL';
no rows selected

select * from user_ind_columns where table_name='USER_TMP_POINT_IMEI_LABEL';
no rows selected  

--7、通过sql_hash_value ,定位到sql 看看
select sql_text from v$sqltext where hash_value=3215935786 order by address,piece;  


--V$SESSION中的常用列
--
--V$SESSION是基础信息视图，用于找寻用户SID或SADDR。不过，它也有一些列会动态的变化，可用于检查用户。如例：
--SQL_HASH_VALUE，SQL_ADDRESS：这两列用于鉴别默认被session执行的SQL语句。如果为null或0，那就说明这个session没有执行任何SQL语句。PREV_HASH_VALUE和PREV_ADDRESS两列用来鉴别被session执行的上一条语句。
--
--注意：当使用SQL*Plus进行选择时，确认你重定义的列宽不小于11以便看到完整的数值。
--
--STATUS：这列用来判断session状态是：
--Achtive：正执行SQL语句(waiting for/using a resource)
--Inactive：等待操作(即等待需要执行的SQL语句)
--Killed：被标注为删除
--下列各列提供session的信息，可被用于当一个或多个combination未知时找到session。
--Session信息
--SID：SESSION标识，常用于连接其它列
--SERIAL#：如果某个SID又被其它的session使用的话则此数值自增加(当一个 SESSION结束，另一个SESSION开始并使用了同一个SID)。
--AUDSID：审查session ID唯一性，确认它通常也用于当寻找并行查询模式
--USERNAME：当前session在oracle中的用户名。

--Client信息
--数据库session被一个运行在数据库服务器上或从中间服务器甚至桌面通过SQL*Net连接到数据库的客户端进程启动，下列各列提供这个客户端的信息
--OSUSER：客户端操作系统用户名
--MACHINE：客户端执行的机器
--TERMINAL：客户端运行的终端
--PROCESS：客户端进程的ID
--PROGRAM：客户端执行的客户端程序
--要显示用户所连接PC的 TERMINAL、OSUSER，需在该PC的ORACLE.INI或Windows中设置关键字TERMINAL，USERNAME。

--Application信息
--调用DBMS_APPLICATION_INFO包以设置一些信息区分用户。这将显示下列各列。
--CLIENT_INFO：DBMS_APPLICATION_INFO中设置
--ACTION：DBMS_APPLICATION_INFO中设置
--MODULE：DBMS_APPLICATION_INFO中设置
--下列V$SESSION列同样可能会被用到：
--ROW_WAIT_OBJ#
--ROW_WAIT_FILE#
--ROW_WAIT_BLOCK#
--ROW_WAIT_ROW#

--V$SESSION中的连接列

--Column View Joined Column(s) 
--SID V$SESSION_WAIT,,V$SESSTAT,,V$LOCK,V$SESSION_EVENT,V$OPEN_CURSOR SID
--(SQL_HASH_VALUE, SQL_ADDRESS) V$SQLTEXT, V$SQLAREA, V$SQL (HASH_VALUE, ADDRESS)
--(PREV_HASH_VALUE, PREV_SQL_ADDRESS) V$SQLTEXT, V$SQLAREA, V$SQL (HASH_VALUE, ADDRESS)
--TADDR V$TRANSACTION ADDR
--PADDR V$PROCESS ADDR
--示例：
--1.查找你的session信息
SELECT SID, OSUSER, USERNAME, MACHINE, PROCESS
FROM V$SESSION WHERE audsid = userenv('SESSIONID');

--2.当machine已知的情况下查找session
SELECT SID, OSUSER, USERNAME, MACHINE, TERMINAL
FROM V$SESSION
WHERE terminal = 'pts/tl' AND machine = 'rgmdbs1';

--查找当前被某个指定session正在运行的sql语句。假设sessionID为100
select b.sql_text 
from v$session a,v$sqlarea b 
where a.sql_hash_value=b.hash_value and a.sid=100
--寻找被指定session执行的SQL语句是一个公共需求，如果session是瓶颈的主要原因，那根据其当前在执行的语句可以查看session在做些什么。

--.如果数据库瓶颈是系统资源(如：cpu,内存)，并且占用资源最多的用户总是停留在某几个服务进程，那么进行如下诸项：
--
--1>.找出资源进程
--2>.找出它们的session,你必须将进程与会话联系起来。
--3>.找出为什么session占用了如此多的资源
--
--2.SQL跟踪文件名是基于服务进程的操作系统进程ID。要找出session的跟踪文件，你必须将session与服务进程联系起来。
--3.某些事件，如rdbms ipc reply，鉴别session进程的Oracle进程ID在等什么。要发现这些进程在做什么，你必须找出它们的session。
--4.你所看到的服务器上的后台进程(DBWR,LGWR,PMON等)都是服务进程。要想知道他们在做什么，你必须找到他们的session。
--V$PROCESS中的常用列
--ADDR：进程对象地址
--PID：oracle进程ID
--SPID：操作系统进程ID
--
--V$PROCESS中的连接列
--Column View Joined Column(s) 
--ADDR V$SESSION PADDR

--示例：
--1.查找指定系统用户在oracle中的session信息及进程id，假设操作系统用户为：junsansi
select s.sid,s.SERIAL#, s.username,p.spid
from v$session s, v$process p
where s.osuser = 'junsansi'
and s.PADDR = p.ADDR

--2.查看锁和等待
SELECT /*+ rule */
lpad(' ', decode(l.xidusn, 0, 3, 0)) || l.oracle_username User_name,
o.owner,o.object_name,o.object_type,s.sid,s.serial#,p.spid
FROM v$locked_object l, dba_objects o, v$session s, v$process p
WHERE l.object_id = o.object_id
AND l.session_id = s.sid and s.paddr = p.addr
ORDER BY o.object_id, xidusn DESC

--附注：
--　　在linux环境可以通过ps查看进程信息包括pid,windows中任务管理器的PID与v$process中pid不能一一对应，这块在oracleDocument中也没有找到介绍，后来google了一下，有资料介绍说是由于windows是多线程服务器,每个进程包含一系列线程。这点于unix等不同，Unix每个Oralce进程独立存在，在Nt上所有线程由Oralce进程衍生。
--　　要在windows中显示oracle相关进程pid，我们可以通过一个简单的sql语句来实现。
SELECT s.SID, p.pid, p.spid signaled, s.osuser, s.program
FROM v$process p, v$session s
WHERE p.addr = s.paddr;


--还可以通过和 v$bgprocess 连接查询到后台进程的名字： 
SELECT s.SID SID, p.spid threadid, p.program processname, bg.NAME NAME
FROM v$process p, v$session s, v$bgprocess bg
WHERE p.addr = s.paddr
AND p.addr = bg.paddr
AND bg.paddr <> '00';

--Eygle大师写了一段sql脚本getsql.sql，用来获取指定pid正在执行的sql语句，在此也附注上来。
--REM getsql.sql
--REM author eygle
--REM 在windows上,已知进程ID,得到当前正在执行的语句
--REM 在windows上,进程ID为16进制,需要转换,在UNIX直接为10进制
SELECT /*+ ORDERED */
sql_text
FROM v$sqltext a
WHERE (a.hash_value, a.address) IN (
SELECT DECODE (sql_hash_value,
0, prev_hash_value,
sql_hash_value
),
DECODE (sql_hash_value, 0, prev_sql_addr, sql_address)
FROM v$session b
WHERE b.paddr = (SELECT addr
FROM v$process c
WHERE c.spid = TO_NUMBER ('&pid', 'xxxx')))
ORDER BY piece ASC

--- 修改字符集
select userenv（'language'） from dual;
如上图所示，字符集是UTF-8，修改字符集的方法如下：

修改数据库字符集为：ZHS16GBK
查看服务器端字符集SQL > select * from V$NLS_PARAMETERS
Telnet到服务器，执行：$sqlplus /nolog
SQL>conn / as sysdba
若此时数据库服务器已启动，则先执行 SHUTDOWN IMMEDIATE 命令关闭数据库服务器，
然后执行以下命令:
SQL>shutdown immediate
SQL>STARTUP MOUNT
SQL>ALTER SYSTEM ENABLE RESTRICTED SESSION;
SQL>ALTER SYSTEM SET JOB_QUEUE_PROCESSES=0;
SQL>ALTER SYSTEM SET AQ_TM_PROCESSES=0;
SQL>ALTER DATABASE OPEN;
SQL>ALTER DATABASE CHARACTER SET ZHS16GBK;（AL32UTF8）

ALTER DATABASE CHARACTER SET AL32UTF8；
ERROR at line 1:ORA-12721: operation cannot execute when other sessions are active
若出现上面的错误，使用下面的办法进行修改，使用INTERNAL_USE可以跳过超集的检查：
SQL>ALTER DATABASE CHARACTER SET INTERNAL_USE ZHS16GBK;

ALTER DATABASE CHARACTER SET INTERNAL_USE AL32UTF8;
SQL>SHUTDOWN IMMEDIATE;
SQL>STARTUP


