--批量设置主键默认值
select 'ALTER TABLE ' || table_name || 'ALTER COLUMN ' || column_name || ' set default nextval(''RISENOA_ZJSW_OA_NW.NEXT'');' from user_tab_columns where NULLABLE='N' and column_name like '%_UNID';

--命令行执行
isql
list user  /show user;
conn user_name/user_password;

--查询模式下的所有主键
select * from v_sys_primary_keys where table_schem = 'schem_name';
--删除表主键
alter table jw_jxrw_wxlsrwkcb drop constraint PK_JW_JXRW_WXLSRWKCB;
--删除索引
drop index PK_JW_JXRW_WXLSRWKCB;

--添加主键
alter table jw_jxrw_wxlsrwkcb add constraint PK_JW_JXRW_WXLSRWKCB primary key(WXLSRWKC_ID);