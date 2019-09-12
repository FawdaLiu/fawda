win下：编辑打开startup.bat

```
SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
```

linux下：编辑打开catalina.sh
```
CATALINA_OPTS="-Xdebug  -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"
```

在tomcat的bin下修改startup.bat （linux即startup.sh） ,在最后一个start前加上jpda 保存