jdk自带监控程序jvisualvm的使用:
1、生产环境tomcat的配置
    编辑应用所在的tomcat服务器下的bin目录下的catalina.sh文件
    配置如下内容：
    export JAVA_OPTS="-Xms256m -Xmx512m -Xss256m -XX:PermSize=512m -XX:MaxPermSize=1024m
    -Djava.rmi.server.hostname=136.64.45.24 -Dcom.sun.management.jmxremote.port=9315 
    -Dcom.sun.management.jmxremote.ssl=false 
    -Dcom.sun.management.jmxremote.authenticate=false"
    