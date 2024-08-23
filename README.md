# 关于我自己写了个OJ这件事
没错，这是一个OJ。众所周知最近的动漫名字都很长，所以这也许算是一种跟风（？）

这个项目是用Spring Boot 3写的后端，前端还没开始。数据库是MariaDB。创建表的命令在initDataBase.sql里。
创建数据库的命令：

```mariadb
create user 'oj'@'localhost' identified by 'oj';
create database oj;
grant all on oj.* to 'oj'@'localhost';
flush privileges ;
```