-- ####################################表空间相关SSSS####################################
-- 1、表空间使用情况
select * from dba_free_space where tablespace_name = 'RISENJAVAOA'

-- 2、查询当前表空间：
select username,default_tablespace from user_users;

-- 3、查询所有表空间：
select tablespace_name from dba_tablespaces;

-- 4、查询表空间路径
select * from dba_data_files;
-- 4.1、查看数据文件相关信息
select * from dba_data_files;

-- 5、创建表空间
-- (create  tablespace shopping --创建shopping表空间
-- datafile 'shopping.dbf'  --物理文件名为shopping.dbf
-- size 50m  --初始大小50M
-- autoextend on  --自动扩展
-- next 50m maxsize 20480m  --每次扩展50M， 最大到20480M
-- extent management local;
-- 表空间：
-- 表空间是数据库的逻辑划分，一个表空间只能属于一个数据库。所有的数据库对象都存放在指定的表空间中。但主要存放的是表， 所以称作表空间。
-- Oracle数据库中至少存在一个表空间，即SYSTEM的表空间。)
create tablespace RISENJAVAOA logging datafile 'E:\app\Administrator\oradata\orcl\RISENJAVAOA.DBF'
 size 512m autoextend on next 512m maxsize 1024m extent management local;

-- 6、删除表空间
DROP TABLESPACE RISENJAVAOA INCLUDING CONTENTS  AND DATAFILES CASCADE CONSTRAINTS;

-- 7、查询表空间剩余大小
select a.tablespace_name,total,free,total-free used from (select tablespace_name,sum(bytes)/1024/1024 total from dba_data_files 
 group by tablespace_name) a,
 ( select tablespace_name,sum(bytes)/1024/1024 free from dba_free_space
 group by tablespace_name) b
 where a.tablespace_name=b.tablespace_name;
-- 或
SELECT tbs 表空间名,                                    
    sum(totalM) 总共大小M,                                    
    sum(usedM) 已使用空间M,                                    
    sum(remainedM) 剩余空间M,                                    
    sum(usedM)/sum(totalM)*100 已使用百分比,                            
    sum(remainedM)/sum(totalM)*100 剩余百分比                            
    FROM(                                            
     SELECT b.file_id ID,                                    
     b.tablespace_name tbs,                                    
     b.file_name name,                                    
     b.bytes/1024/1024 totalM,                                    
     (b.bytes-sum(nvl(a.bytes,0)))/1024/1024 usedM,                        
     sum(nvl(a.bytes,0)/1024/1024) remainedM,                            
     sum(nvl(a.bytes,0)/(b.bytes)*100),                                
     (100 - (sum(nvl(a.bytes,0))/(b.bytes)*100))                            
     FROM dba_free_space a,dba_data_files b                            
     WHERE a.file_id = b.file_id                                
     GROUP BY b.tablespace_name,b.file_name,b.file_id,b.bytes                    
     ORDER BY b.tablespace_name                                
    )                                            
    GROUP BY tbs     

-- 8、重指定表空间大小
alter database datafile 'F:\APP\ADMINISTRATOR\ORADATA\ORCL\RISENJAVAOA.DBF' resize 1536m

-- 9、表空间自动增长
alter database datafile 'F:\APP\ADMINISTRATOR\ORADATA\ORCL\RISENJAVAOA.DBF' autoextend on next 50m maxsize 500m;    

-- 10、附加表空间文件
alter tablespace YYGOV add datafile 'd:\users02.dbf' size 5m;

-- 11、清空表空间
declare
    v_name all_tables.table_name%type;
    cursor mycur is select table_name from all_tables where owner='TEST';
begin
    open mycur;
    loop
        fetch mycur into v_name;
        exit when mycur%NOTFOUND OR mycur%NOTFOUND IS NULL;
        execute immediate 'drop table '|| v_name;
    end loop;
    close mycur;
end;
--或删除所有视图
declare
    v_name all_views.view_name%type;
    cursor mycur is select view_name from all_views where owner='TEST';
begin
    open mycur;
    loop
        fetch mycur into v_name;
        exit when mycur%NOTFOUND OR mycur%NOTFOUND IS NULL;
        execute immediate 'drop view '|| v_name ;
    end loop;
    close mycur;
end;

-- 12、表的表空间
    -- 12.1、创建
    create table tablename (id number, name varchar2(48)) tablespace tablespacename;
    -- 12.2、修改
    alter table tablename move tablespace newtablespcace;
    -- 修改表空间需要重建索引，
    -- 普通版
    alter index indexname rebuild;
    -- 企业版
    alter index indexname rebuild online;
    
    -- 创建索引也可以指定表空间，如下列语句：
    -- 复合索引
    create index indexname on tablename (ROUTE_CRT_ORG_CODE,ROUTE_RCV_ORG_CODE) tablespace tablespacename;
    -- 主键
    alter table tablename add primary key (columns) using index tablespace tablespacename;



-- ####################################表空间相关EEEE####################################



-- ####################################用户相关SSSS####################################
-- 1、创建用户
create user XHTY identified by 123456 default tablespace RISENJAVAOA;
-- 指定限额
--CREATE USER javaoa PROFILE DEFAULT 
--    IDENTIFIED BY 123456 DEFAULT TABLESPACE RISENJAVAOA
--    TEMPORARY TABLESPACE TEMP
--    ACCOUNT UNLOCK;
--revoke unlimited tablespace from javaoa;
--alter user javaoa quota 0 on YYGOV;
--alter user javaoa quota unlimited on RISENJAVAOA;


-- 2、授权用户角色
--系统权限分类：
--DBA: 拥有全部特权，是系统最高权限，只有DBA才可以创建数据库结构。
--RESOURCE:拥有Resource权限的用户只可以创建实体，不可以创建数据库结构。
--CONNECT:拥有Connect权限的用户只可以登录Oracle，不可以创建实体，不可以创建数据库结构。
--对于普通用户：授予connect, resource权限。
--对于DBA管理用户：授予connect，resource, dba权限。

--dba
--查询具有dba权限的用户
select * from dba_role_privs where granted_role='DBA'
--回收dba
revoke dba from user_Name
grant dba to xhty
--(1.grant create session to test;--赋予create session的权限
-- 2.grant create table,create view,create trigger, create sequence,create procedure to test;--分配创建表，视图，触发器，序列，过程 权限
-- 3.grant unlimited tablespace to test; --授权使用表空间)

-- 授予最终用户的典型权利，最基本的
GRANT CONNECT TO xhgov;
-- 授予数据库维护人员的 
GRANT IMP_FULL_DATABASE TO xhgov;
-- 授予开发人员的
GRANT RESOURCE TO xhgov;
--ORA-01950: 对表空间 'RISENPT' 无权限
--解决办法：赋予表空间权限
alter user username quota umlimited on tablespacename;
--或者，如果不想赋予DBA角色权限的话
grant resource to username

-- 3、删除用户及用户下所有的表
--drop user xxxx 只有在用户下没有其他对象的时候才能用。如果user下有有表，使用drop user会提示错误。
--drop user xxxx cascade删除用户及用户下所有的对象，表、过程什么的。一次性把用户及用户下的对象都删了(主键、外键、索引)
drop user xxxx cascade;

-- 4、查看当前登陆用户
select * from user_users;

-- 5、查看该系统下所有用户 
select * from dba_users; 
-- 6、删除所有表
select 'drop table '||table_name||';' as sqlscript from user_tables;

-- ####################################用户相关EEEE####################################




-- ####################################导入导出SSSS####################################
-- 1、解决11g空表无法导出的问题
      --先查询一下哪些表是空的：Sql代码 
select table_name from user_tables where NUM_ROWS=0; 
      --下面我们通过select 来生成修改语句：Sql代码 
select 'alter table '||table_name||' allocate extent;' from user_tables where num_rows=0 

--先导数据rows=y indexes=n，再导索引rows=n indexes=y
imp user2/pwd fromuser=user1 touser=user2 file=file commit=y feedback=10000 buffer=10240000 ignore=y rows=y indexes=n  
   
imp user2/pwd fromuser=user1 touser=user2 file=file commit=y feedback=10000 buffer=10240000 ignore=y rows=n indexes=y
-- ####################################导入导出EEEE####################################




-- ####################################索引主键SSSS####################################
-- 1、查询空间下的主键
select segment_name,partition_name,tablespace_name from dba_extents where tablespace_name=upper('YYGOV');
-- 2、以下是生成删除主键约束的脚本
select 'alter table '||owner||'.'||table_name||' drop constraint '||constraint_name||' ;' from dba_constraints where constraint_type in ('U', 'P') and (index_owner, index_name) in (select owner, segment_name from dba_segments where tablespace_name = 'YYGOV');

--3 重建索引
--3.1、手动查询重建
--(1)先查询失效索引，语句为：
select index_name ,status  from user_indexes where Status = 'UNUSABLE' ;
--(2)重建索引,语句为：
alter index  xxx rebuild;
--手动操作有个快捷的方式，可以将上面的步骤合成为一个查询语句：
select 'alter index ' || index_name || ' rebuild;' from user_indexes where Status = 'UNUSABLE' ;
-- 3.2、将上面的手动操作做成存储过程执行的“只能自动”
--(1)创建重建索引的存储过程，语句如下：
create or replace procedure p_rebuild_all_index
   (tablespace_name in varchar2,--这里是表空间名，如果不改变表空间，可以传入null
   only_unusable in boolean)    --是否仅对无效的索引操作
as
   sqlt varchar(200);
begin
    --只取非临时索引
    for idx in (select index_name, tablespace_name, status from user_indexes where temporary = 'N') loop
        --如果是如重建无效的索引，且当索引不是无效时，则跳过
        if only_unusable = true and idx.status <> 'UNUSABLE' then
           goto continue;
        end if;

        if (tablespace_name is null) or idx.status = 'UNUSABLE' then
           --如果没有指定表空间，或索引无效，则在原表空间重建
           sqlt := 'alter index ' || idx.index_name || ' rebuild ';
        elsif upper(tablespace_name) <> idx.tablespace_name then
           --如果指定的不同的表空间，则在指定表空间待建索引
           sqlt := 'alter index ' || idx.index_name || ' rebuild tablespace ' || tablespace_name;
        else
           --如果表空间相同，则跳过
           goto continue;
        end if;

        dbms_output.put_line(idx.index_name);
        EXECUTE IMMEDIATE sqlt;
        <<continue>>
        null;
     end loop;
end;
--(2) 创建执行重建的存储过程
CREATE OR REPLACE PROCEDURE EXEC_REBUILD_PROC AS
    v_err_num  NUMBER;  --ORA错误号
   v_err_msg  VARCHAR2(100); --错误描述
BEGIN

-- 4、代表创建10天的分区，tablespace代表表空间名
 p_rebuild_all_index(NULL,true);
 COMMIT;
EXCEPTION
 WHEN OTHERS THEN
  v_err_num := SQLCODE;
  v_err_msg := SUBSTR(SQLERRM, 1, 100);
  dbms_output.put_line('EXEC_REBUILD_PROC执行出现异常,错误码='|| v_err_num || '错误描述=' || v_err_msg);
END EXEC_REBUILD_PROC;

-- 5添加、启用、禁用、删除主键
-- (1)查询当前用户所有表
select count(*) from tabs;
SELECT count(*) FROM ALL_TABLES WHERE OWNER='RISENJAVAOA';
-- (2)查询没有主键的表
select table_name from user_tables a where not exists (select * from user_constraints b where b.constraint_type = 'P' and a.table_name = b.table_name) ORDER BY table_name
-- (3)创建主键
-- 一:创建表的同时创建主键约束，
-- 一.1:无命名，
create table table_name (colom_id int primary key not null,name varchar2(20));
-- 一.2:有命名，
create table table_name (colom_id int ,name varchar2(20),constraint ixd_id primary key(id));
-- 二:向表中添加主键约束
alter table jack add constraint pk_id primary key(object_id);
--另外当索引创建好以后再添加主键的效果
create index ind_object_id on jack(object_id);
alter table jack add constraint pk_id primary key(object_id);
-- 三：修改主键约束
-- 三.1、禁用/启用主键
alter table jack disable primary key;
alter table jack enable primary key;
-- 三.2、重命名主键
alter table jack rename constraint pk_id to pk_jack_id;
--四：删除表中已有的主键约束
-- 四.1、无命名
select owner,constraint_name,table_name,column_name from user_cons_columns where table_name = 'CORE_LOG';
select table_name,index_name from user_indexes where table_name='CORE_LOG';
alter table jack drop constraint SYS_C0011105;
-- 四.2、有命名
select owner,constraint_name,table_name,column_name from user_cons_columns where table_name = 'CORE_LOG';
alter table jack drop constraint IXD_ID;
-- 四.3、批量删除
select 'alter table ' || u.table_name || ' drop constraint ' || t.constraint_name || ';' from user_cons_columns t left join user_tables u
on t.owner = 'RISEN_QTXQ_OA' and t.constraint_name like 'SYS_%';

--五：sql拼接，批量创建主键
--查询没有主键的表
select table_name from user_tables a where not exists (select * from user_constraints b where b.constraint_type = 'P' and a.table_name = b.table_name) ORDER BY table_name
--先查询出某一字段重复的记录，此记录需要手动创建
select a.TABLE_NAME,count(a.TABLE_NAME)
from user_tables a join user_tab_columns t on a.TABLE_NAME = t.TABLE_NAME
where not exists (select *
    from user_constraints b
    where b.constraint_type = 'P'
    and a.table_name = b.table_name)
and t.COLUMN_NAME like '%_UNID'
group by a.TABLE_NAME having count(a.TABLE_NAME) > 1
--在重复记录修改完之后，如果主键列有重复数据，会提示违反主键，需手动处理          
select * from CORE_AMG t where t.cramg_unid in(select t.cramg_unid from CORE_AMG t group by t.cramg_unid having count(t.cramg_unid) > 1) for update
--没有重复的记录
select 
'alter table ' || a.table_name || ' add constraint PK_' || REPLACE(t.COLUMN_NAME,'_','') || ' primary key('||t.COLUMN_NAME||');'
from user_tables a join user_tab_columns t on a.TABLE_NAME = t.TABLE_NAME
where not exists (select *
    from user_constraints b
    where b.constraint_type = 'P'
    and a.table_name = b.table_name)
and t.COLUMN_NAME like '%_UNID'
--注：执行过程中遇到报错，继续执行，执行完之后，再运行一次上面的sql 查出来不能创建的表，找下具体原因很有可能是数据存在unid重复的记录或主键名称已被使用
--      如：PK_CRMGPUNID 将此名称改为PK_CRMGP_UNID 
select 
'alter table ' || a.table_name || ' add constraint PK_' || REPLACE(t.COLUMN_NAME,'_','_') || ' primary key('||t.COLUMN_NAME||');' 
from user_tables a join user_tab_columns t on a.TABLE_NAME = t.TABLE_NAME
where not exists (select * from user_constraints b where b.constraint_type = 'P'
 and a.table_name = b.table_name)
and t.COLUMN_NAME like '%_UNID'

-- ####################################索引主键EEEE####################################




-- ####################################同义词SSSS####################################
-- 1、同义词
--CREATE [PUBLIC] SYNONYM synonym FOR object;
-- 1.1、一般来说，Oracle里，中括号里面的内容是可选内容，在这里，如果加上PUBLIC表示公用同义词，不加的话就是私有同义词；
-- 1.2、synonym 这里表示同义词的名字
-- 1.3、object表示要创建同义词的对象
-- 比如要为Scott方案用户分配检索Hr方案用户的jobs表的权限的话，需要这么操作：
CREATE SYNONYM hrjobs FOR HR.jobs;
create synonym core_account for xhty.core_account;
create synonym core_organization for xhty.core_organization;
create synonym core_account_organiz for xhty.core_account_organiz;

-- 2、创建同义词
-- 2.1普通用法如下所示：
CREATE [OR REPLACE] [PUBLIC] SYNONYM [ schema.] 同义词名称 FOR [ schema.] object [ @dblink ];
-- 2.2专有（私有）同义词
CREATE SYNONYM SYSN_TEST FOR TEST;-- Create public synonym CORE_ACCOUNT for risenjavaoa.core_account
-- 2.3公共同义词
CREATE PUBLIC SYNONYM PUBLIC_TEST FOR TEST;

-- 2.4、如果要创建一个远程的数据库上的某张表的同义词，需要先创建一个Database Link(数据库连接)来扩展访问，
--然后再使用如下语句创建数据库同义词：create synonym table_name for table_name@DB_Link;
--公共同义词是和用户的schema无关的，但是公共的意思并不是所有的用户都可以访问它，必须被授权后才能进行；私有同义词是schema的对象
-- 3、查看同义词：
SELECT * FROM DBA_SYNONYMS WHERE SYNONYM_NAME IN ( 'SYSN_TEST','PUBLIC_TEST');
SELECT * FROM USER_SYNONYMS；
-- 4、使用同义词
SELECT * FROM SYSN_TEST;
--使用同义词可以保证当数据库的位置或对象名称发生改变时，应用程序的代码保持稳定不变，仅需要改变同义词；
--当使用一个没有指定schema的同义词是，首先在用户自己的schema中寻找，然后再公共同义词中寻找
-- 5、删除同义词
--DROP PUBLIC SYNONYM core_account;
DROP [ PUBLIC ] SYNONYM [ schema. ] 同义词名称 [ FORCE ];
DROP SYNONYM SYSN_TEST;
-- 5.1、当同义词的原对象被删除是，同义词并不会被删除
DROP PUBLIC SYNONYM PUBLIC_TEST;
-- 6、编译同义词
-- 6.1、当同义词的原对象被重新建立时，同义词需要重新编译
ALTER  SYNONYM T COMPILE; 
-- 6.2、对原对象进行DDL操作后，同义词的状态会变成INVALID；当再次引用这个同义词时，同义词会自动编译，状态会变成VALID，无需人工干预，当然前提是不改变原对象的名称
--存在同名对象和公共同义词时，数据库优先选择对象作为目标，存在同名私有对象和公共对象时，数据库优先选择私有同义词作为目标
SELECT OBJECT_NAME, STATUS  FROM ALL_OBJECTS WHERE OBJECT_NAME='T';

-- ####################################同义词EEEE####################################




-- ####################################数据库相关SSSS####################################
--1、数据库
--数据库是数据集合。Oracle是一种数据库管理系统，是一种关系型的数据库管理系统。
--通常情况了我们称的“数据库”，并不仅指物理的数据集合，他包含物理数据、数据库管理系统。也即物理数据、内存、操作系统进程的组合体。
--我们在安装Oracle数据库时，会让我们选择安装启动数据库（即默认的全局数据库）
--全局数据库名：就是一个数据库的标识，在安装时就要想好，以后一般不修改，修改起来也麻烦，因为数据库一旦安装，数据库名就写进了控制文件，数据库表，很多地方都会用到这个数据库名。
--启动数据库：也叫全局数据库，是数据库系统的入口，它会内置一些高级权限的用户如SYS，SYSTEM等。我们用这些高级权限账号登陆就可以在数据库实例中创建表空间，用户，表了。
--查询当前数据库名：
select name from v$database;

--2、数据库实例
--用Oracle官方描述：实例是访问Oracle数据库所需的一部分计算机内存和辅助处理后台进程，是由进程和这些进程所使用的内存(SGA)所构成一个集合。
--其实就是用来访问和使用数据库的一块进程，它只存在于内存中。就像Java中new出来的实例对象一样。
--我们访问Oracle都是访问一个实例，但这个实例如果关联了数据库文件，就是可以访问的，如果没有，就会得到实例不可用的错误。
--实例名指的是用于响应某个数据库操作的数据库管理系统的名称。她同时也叫SID。实例名是由参数instance_name决定的。
--查询当前数据库实例名：
select instance_name from v$instance;
--数据库实例名(instance_name)用于对外部连接。在操作系统中要取得与数据库的联系，必须使用数据库实例名。比如我们作开发，要连接数据库，就得连接数据库实例名：
--jdbc:oracle:thin:@localhost:1521:orcl（orcl就为数据库实例名）
--一个数据库可以有多个实例，在作数据库服务集群的时候可以用到。

--3、表空间
--Oracle数据库是通过表空间来存储物理表的，一个数据库实例可以有N个表空间，一个表空间下可以有N张表。
--有了数据库，就可以创建表空间。
--表空间(tablespace)是数据库的逻辑划分，每个数据库至少有一个表空间（称作SYSTEM表空间）。
--为了便于管理和提高运行效率，可以使用一些附加表空间来划分用户和应用程序。
--例如：USER表空间供一般用户使用，RBS表空间供回滚段使用。一个表空间只能属于一个数据库。
--创建表空间语法：
--Create TableSpace 表空间名称  
--DataFile          表空间数据文件路径  
--Size              表空间初始大小  
--Autoextend on
--如：
--create tablespace db_test  
--datafile 'D:\oracle\product\10.2.0\userdata\db_test.dbf'  
--size 50m 
--autoextend on;
--查看已经创建好的表空间：
select default_tablespace, temporary_tablespace, d.username from dba_users d


-- 1 PLSQL 经常自动断开失去连接的解决过程
-- 1.1、去查看以下CONNECT_TIME的连接时间
select * from dba_profiles t where t.resource_name='CONNECT_TIME';
-- 那如何找到powerdesk用户所属于的那个profile呢？去查看当前用户的CONNECT_TIME
select * from user_resource_limits;
-- 看到CONNECT_TIME是UNLIMITED，没有限制
-- 1.2，cmd里面去用sqlplus连接验证
-- sqlplus连接上，过去30分钟不操作，再次操作连接db，正常使用，看来这和oracledb无关了，应该要去看看plsql的设置了
-- 1.3，去plsql里面设置
-- 进入plsql的Tools，再进入Preferences，然后选择左边的Connection选型，选择Check connection

-- ####################################数据库相关EEEE####################################



-- ####################################错误相关SSSSS####################################

--ORA-01045: user USERNAME lacks CREATE SESSION privilege;logon denied
--使用系统用户登录后，使用如下sql语句给出错用户赋权限,XXX是登录出错的用户名
grant create session to XXX;

--Dynamic Performance Tables not accessible, Automatic Statistics disable for this session.
--You can disable statistics in the preference menu, or obtain select privileges on the v$session, v$sesstat and v$statname tables
--动态性能表不可访问，本次会话禁用自动统计。您可以禁用首选项菜单中的统计信息，或者在V$session、$$SeSTAT和V$STATNEX表上获得选择特权。
--plsql:打开客户端头部菜单Toos-Tools->Preferences->Options->Automatic statistics的勾选去掉
--用dba执行下面这句或者在pl/sql中找到username，然后在edit中选择“System privileges”tab，增加一个“select any dictionary”权限。 ;
grant SELECT ANY DICTIONARY to username
-- ####################################错误相关EEEEE####################################


-- ####################################误删除SSSSS####################################
--truncate table xxx 执行后，跑路吧
--以下是没有进行永久删除的，如：plsql的compare table data, drop, 
--1、较low的恢复，并且有表的情况下
--1.1、查询删除的时间点
select * from CORE_ACCOUNT as of timestamp to_timestamp('2018-05-14 18:40:32','yyyy-mm-dd hh24:mi:ss');

--1.2、创建批量查询语句
select 'select * from ' || t.TABLE_NAME || ' as of timestamp to_timestamp(' ||'''2018-05-14 18:40:32' ||''','||'''yyyy-mm-dd hh24:mi:ss' ||''');' 
    from dba_tables t where t.owner='RISENPT_HZRISEN_OA2' and t.NUM_ROWS > 0 and t.SAMPLE_SIZE <> 0;
--ORA-08189:因为未开启行移动功能，不能闪退回标
alter table table_name enable row movement;
--关闭行移动
alter table table_name disable row movement;
    
    
--2.表恢复、数据恢复，执行了drop之后，进行恢复在当前用户下就可以执行
--2.1. 表恢复
--当一个表删除的时候，与之相关的空间并没有被删除，他只是被重命名并放在回收站中了，flash backdrop就是基于回收站找回数据的。
--对误删的表，只要没有使用 purge 永久删除选项，那么基本上是能从 flashback table 区恢复回来的。
--数据表和其中的数据都是可以恢复回来的，记得 flashback table 是从 Oralce 10g 提供的，一般步骤有：

--a.从 flashback table 里查询被删除的数据表,当前用户下执行
select * from recyclebin order by droptime desc
--b.执行表的恢复，表中的主键索引及数据也会相继恢复
flashback table '需要恢复的表名' to before drop
--批量语句：
select 'flashback table ' || t.TABLE_NAME || ' to before drop;' from dba_tables t where owner='RISENPT_HZRISEN_OA2' and t.SAMPLE_SIZE <> 0;


--2.2. 表数据恢复
--对误删的表记录，只要没有 truncate 语句，就可以根据事务的提交时间进行选择恢复。
--这功能也是  oracle 10g 以上提供的，一般步骤有：
--a. 先从 flashback_transaction_query 视图里查询，视图提供了供查询用的表名称、事务提交时间、undo_sql等字段。
select * from flashback_transaction_query where table_name='需要恢复数据的表名（大写）';
--b.查询删除的时间点
select to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') time, to_char(dbms_flashback.get_system_change_number) scn from dual;

--或者你知道大概记得删除点，你也可以这样试试查询，找出删除前的时间点
select * from '需要恢复数据的表名' as of timestamp to_timestamp('时间点', 'yyyy-mm-dd hh24:mi:ss');

--c.进行数据恢复
--通过第二步找到数据丢失的时间点，恢复极为简单，语句为
flashback table '需要恢复数据的表名' to timestamp to_timestamp('数据丢失的前一时间点','yyyy-mm-dd hh24:mi:ss');
--批量语句
select 'flashback table ' || t.TABLE_NAME || ' to timestamp to_timestamp(' ||'''2018-05-14 18:40:32' ||''','||'''yyyy-mm-dd hh24:mi:ss' ||''');' from dba_tables t where owner='RISENPT_HZRISEN_OA2' and t.SAMPLE_SIZE <> 0;


--注意：在执行上述操作的时候，需要允许 oracle 修改分配给行的 rowid,这时候 oracle 需要给恢复的数据分配新的物理地址。
--ORA-08189:因为未开启行移动功能，不能闪退回标
alter table table_name enable row movement;
--关闭行移动
alter table table_name disable row movement;


--其实找到数据丢失前的时间点后，恢复数据也可以将需要恢复的数据直接插入到目标表中
insert into '数据丢失的表' select * from t of timestamp to_timestamp('时间点', 'yyyy-mm-dd hh24:mi:ss') where .......;


--3、闪回表要用到UNDO表空间信息，是一个删除新数据，重新插入老数据的过程，因此row movement的属性要打开。
select current_scn from  v$database;

flashback table lihm.t to scn 2235951;
--ORA-08189:因为未启用行移动功能,不能闪回表
alter table table_name enable row movement;

--4、Flashback Database
--闪回数据库是通过撤销对数据修改的方法来完成的，完成闪回要达到以下条件
--1）  没有数据文件丢失或损坏
--2）  不能闪回到控制文件重建之前
--查看闪回特性是否启用：
select flashback_on from v$database;
--NO
--启用闪回特性：
shutdown immediate;
startup mount;
alter database flashback on;
alter database open;
select flashback_on from v$database;
--查看闪回窗口时间：
SELECT OLDEST_FLASHBACK_SCN,OLDEST_FLASHBACK_TIME FROM V$FLASHBACK_DATABASE_LOG;
--OLDEST_FLASHBACK_SCNOLDEST_FLASHBACK_TI
--2258659 2013-08-14 16:28:44
--创建还原点：Normalrestore point：
create restore point test1;
--还原点已创建。
--Guaranteerestore point
create restore point test2guarantee flashback database;
--还原点已创建。
--查看还原点类型：
l
SELECT NAME, SCN, TIME, DATABASE_INCARNATION#, GUARANTEE_FLASHBACK_DATABASE FROM V$RESTORE_POINT

--NAME         SCN TIME                      DATABASE_INCARNATION# GUARAN
------------------------------------------------------------ ----------
--TEST2        2260674 14-8月 -1304.49.20.000000000 下午             2 YES
--TEST1        2259852 14-8月 -1304.36.32.000000000 下午             2 NO

--RMAN也可以查看还原点：
list restore point all;

--SCN              RSP 时间   类型       时间       名称
--------------------------  ---------- ---------- ----
--2259852                                14-8月-13 TEST1
--2260674                     GUARANTEED 14-8月-13 TEST2
--下面我们还原到最早的时间：
shutdown immediate;
startup mount;
SELECTOLDEST_FLASHBACK_SCN, OLDEST_FLASHBACK_TIME FROM V$FLASHBACK_DATABASE_LOG;

--OLDEST_FLASHBACK_SCNOLDEST_FLASHBA
----------------------------------
--    2258659 14-8月 -13

flashback databaseto scn 2258659;
--ORA-01219:数据库或可插入数据库未打开:仅允许在固定表或视图中查询
alter database open read only;
--至此数据库已经闪回并打开，我们可以把丢失数据找回来，或openresetlogs打开，
--如果想flashback到更早的时间，只要日志支持就可以执行flashback database to scn xxx命令来实现，
--如果想恢复到闪回前的状态，可以使用recover database命令.
--查看闪回日志可以用：
select name,bytes,type from V$FLASHBACK_DATABASE_LOGFILE;
--NAME                          BYTES TYPE
-------------------------------------------------- ------------------

--/u01/oracle/fast_recovery_area/ORCL/flas   52428800 NORMAL
--hback/o1_mf_90phxvgz_.flb
--/u01/oracle/fast_recovery_area/ORCL/flas   52428800 RESERVED
--hback/o1_mf_90phxyvp_.flb

--关闭闪回数据库特性前，最好删除相应还原点，再删除flashback log，不然下次启动时会检测闪回日志。
-- ####################################误删除EEEEE####################################


-- ####################################表操作SSSSS####################################
--1、表列由VARCHAR2类型改成CLOB
--1.1、把原来表中该列重命名
alter table CORE_FLEX_FORM rename column CFFRM_CONTENT to CFFRM_CONTENT_BAK;  
--1.2、在表中增加该列，并指定改列类型为clob，注意备注问题
alter table CORE_FLEX_FORM add CFFRM_CONTENT clob;
--1.3、对此列包含数据的需要包数据从步骤一重命名列中拷出(对于此列没有数据的此步骤省略)
update CORE_FLEX_FORM set CFFRM_CONTENT=CFFRM_CONTENT_BAK;
commit;
--1.4、删除步骤一中的备份列
alter table CORE_FLEX_FORM drop column CFFRM_CONTENT_BAK;
--1.5、验证是否成功


-- ####################################表操作EEEEE####################################

--阅读数：1646
--第一步：添加一个clob类型的字段
--alter table nlphistory_pat add (answer_bak clob);
--第二部：将原来字段的值拷贝到新建的clob字段
update nlphistory_pat set answer_bak = answer ;
--第三步：删除原来的字段
alter table nlphistory_pat drop column answer;
--第四步：将新建的clob字段的名字修改为原来的字段的名字
alter table nlphistory_pat rename column answer_bak to answer;
--第五步：提交
commit;

--批量创建表注释
select 'comment on table '|| table_name || ' is ''' ||  comments || ''';' from user_tab_comments where comments is not null;
--批量创建字段注释
select 'comment on column ' ||  table_name || '.' || column_name || ' is ''' || comments || ''';' from user_col_comments ct where ct.COMMENTS is not null and table_name in (select distinct(t.TABLE_NAME) from user_tables t)
-- ####################################表操作EEEEE####################################
