
docker pull mysql:5.7.26


docker run -p 3306:3306 --name tinyfMysql -v "$PWD/conf":/etc/mysql/conf.d 
  -v "$PWD/logs":/logs -v "$PWD/data":/var/lib/mysql 
  -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7.26 
  --lower_case_table_names=1 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  
-- 将容器中的mysqld.cnf复制到 conf.d目录下修改并替换回来 重启生效
sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION