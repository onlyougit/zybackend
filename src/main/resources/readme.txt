1、不要把一些共享资源放到WEB-INF目录下，在访问的时候会报找不到资源。如图片、css、js等
2、mvn mybatis-generator:generate
3、${pageContext.request.contextPath}
4、var message = mini.decode(text);
5、BeanUtils.copyProperties
6、解决远程连接mysql慢：在/etc/my.cnf中[mysqld]下加一行skip-name-resolve
7、如果mysql报这个错：Access denied for user ''@'localhost' to database 'mysql'
原因：是因为mysql数据库的user表里，存在用户名为空的账户即匿名账户，导致登录的时候是虽然用的是root，但实际是匿名登录的
解决方法：
<1>.关闭mysql
   # service mysqld stop
<2>.屏蔽权限
   # mysqld_safe --skip-grant-table
   屏幕出现： Starting demo from .....
<3>.新开起一个终端输入
   # mysql -u root mysql
   mysql> delete from user where USER='';
   mysql> FLUSH PRIVILEGES;//记得要这句话，否则如果关闭先前的终端，又会出现原来的错误
   mysql> \q