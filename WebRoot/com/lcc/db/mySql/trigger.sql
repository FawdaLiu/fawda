# 在与第三方企业合作时，pd规范未注意到，导致我方必须要整改，因为驾驶舱需要统计，故通过触发器解决
# 在使用触发器时必须了解到触发器的弊端，想办法避免，1.触发器的事务：原子性，一致性，隔离性，持久性
# 优点：
# SQL触发器提供了检查数据完整性的替代方法。
# SQL触发器可以捕获数据库层中业务逻辑中的错误。
# SQL触发器提供了运行计划任务的另一种方法。通过使用SQL触发器，您不必等待运行计划的任务，因为在对表中的数据进行更改之前或之后自动调用触发器。
# SQL触发器对于审核表中数据的更改非常有用。
# 缺点：
# SQL触发器只能提供扩展验证，并且无法替换所有验证。一些简单的验证必须在应用层完成。 例如，您可以使用JavaScript或服务器端使用服务器端脚本语言(如JSP，PHP，ASP.NET，Perl等)来验证客户端的用户输入。
# 从客户端应用程序调用和执行SQL触发器不可见，因此很难弄清数据库层中发生的情况。
# SQL触发器可能会增加数据库服务器的开销。
# 如果触发器执行失败将导致整个执行过程回滚
# 语法：
# create trigger trigger_name trigger_time trigger_event(insert|update|delete) on table_name for each row
# begin
#   ...
# end

# new：当触发插入和更新事件时可用，指向的是被操作的记录
# old： 当触发删除和更新事件时可用，指向的是被操作的记录
DROP TRIGGER IF EXISTS `after_insert_common_unpass_reason`;
delimiter ;;
CREATE TRIGGER `after_insert_common_unpass_reason` AFTER INSERT ON `qqdz_common_unpass_reason` FOR EACH ROW begin
if not exists (select * from py_330105_004_common_unpass_reason t
where t.id=new.id) then
	INSERT INTO py_330105_004_common_unpass_reason select * from qqdz_common_unpass_reason t where t.id = new.id;
else
	update py_330105_004_common_unpass_reason set credit_code=new.credit_code,apply_form_id=new.apply_form_id,reason_code=new.reason_code,reason_desc=new.reason_desc,create_time=new.create_time,check_user_id=new.check_user_id,check_user_name=new.check_user_name where id=new.id;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table qqdz_common_unpass_reason
-- ----------------------------
DROP TRIGGER IF EXISTS `after_update_common_unpass_reason`;
delimiter ;;
CREATE TRIGGER `after_update_common_unpass_reason` AFTER UPDATE ON `qqdz_common_unpass_reason` FOR EACH ROW begin
if not exists (select * from py_330105_004_common_unpass_reason t
where t.id=new.id) then
	INSERT INTO py_330105_004_common_unpass_reason select * from qqdz_common_unpass_reason t where t.id = new.id;
else
	update py_330105_004_common_unpass_reason set credit_code=new.credit_code,apply_form_id=new.apply_form_id,reason_code=new.reason_code,reason_desc=new.reason_desc,create_time=new.create_time,check_user_id=new.check_user_id,check_user_name=new.check_user_name where id=new.id;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table qqdz_common_unpass_reason
-- ----------------------------
DROP TRIGGER IF EXISTS `after_delete_common_unpass_reason`;
delimiter ;;
CREATE TRIGGER `after_delete_common_unpass_reason` AFTER DELETE ON `qqdz_common_unpass_reason` FOR EACH ROW begin
delete from py_330105_004_common_unpass_reason where id = old.id;
end
;;
delimiter ;