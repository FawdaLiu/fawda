--工作当中有时候想查看一下数据库的具体版本号，这里简单整理了一下，
--可以通过：dba_server_registry、dba_registry_history、product_component_version、v$version视图查看，
--另外还可以通过Oracle Opatch命令查询具体的补丁版本
--1、查询dba_server_registry视图
select comp_name,version from dba_server_registry;
--2、查询dba_registry_history
select * from dba_registry_history;
--3、查询product_component_version视图
select product,version from product_component_version;
--4、查询v$version视图
select * from v$version;
--5、利用Opatch命令进行查看具体的补丁有哪些
$opatch lsinventory -detail -oh $CRS_HOME
$opatch lsinventory -detail -oh $RDBMS_HOME

set pages 100 lines 120
col action format a6
col namespace format a10
col version format a10
col comments format a15
col action_time format a30
col bundle_series format a15
alter session set nls_timestamp_format = 'yyyy-mm-dd hh24:mi:ss.ff';
select * from dba_registry_history;
