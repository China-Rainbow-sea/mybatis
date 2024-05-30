# #{}和${}的区别：

在排序 order in 中
* #{}的执行结果
  2024-05-30 21:04:43.101 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectAllByAscOrDesc - ==>  Preparing: select id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car order by produce_time ?
  2024-05-30 21:04:43.133 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectAllByAscOrDesc - ==> Parameters: asc(String)

org.apache.ibatis.exceptions.PersistenceException:
### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds 
to your MySQL server version for the right syntax to use near ''asc'' at line 10
### 


${}的执行结果

2024-05-30 21:07:06.654 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectAllByAscOrDesc - ==>  Preparing: select id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car order by produce_time asc
2024-05-30 21:07:06.688 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectAllByAscOrDesc - ==> Parameters:
2024-05-30 21:07:06.716 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectAllByAscOrDesc - <==      Total: 2

区别一:
> #{}：底层使用PreparedStatement，特点: 先进行SQL语句的编译，然后给SQL语句的占位符问号 ？ 传值，可以避免SQL注入的问题，（传入的值，作为参数添加到SQL语句当中，同时SQL语句是已经编译好了的）
> ${}：底层使用Statement，特点:先进行SQL语句的拼接，然后再对SQL语句进行编译，存在SQL语句的风险，（简单的说就是，直接将传入的值拼接为了SQL语句）


**如果需要执行的SQL语句的关键字放到SQL语句中，只能使用 ${},因为 #{}是以值的形式放到SQL语句当中的** 

2.向SQL语句当中拼接表名，就需要使用${}
现实业务当中，可能存在分表存储的数据的情况，因为一张表存的话，数据量太大了，查询效率比较低。
可以将这些数据有规律的分表存储，这样在查询的时候效率就比较高，因为扫描的数据量变小了
日志表，专门存储日志信息，如果t_long 只有一张表，这张表中每一天都会产生很多的log,慢慢的，这个表中数据会很多，
怎么解决呢
    可以每天生成一个新表，每张表以当天日期作为名称，例如：
    t_log_202209801
    t_log_20220902
你想知道某一天的日志信息怎么办呢？
  假设今天20220901，那么直接查：t_log-20220901的表即可。
>    <!--	id 要是 namespace 对应接口上的方法名: -->
    <select id="selectAllByTable" resultType="com.rainbowsea.mybatis.pojo.Log">
    select * 
    from t_log_${}
    </select>

${}
2024-05-30 21:43:28.777 [main] DEBUG c.r.mybatis.mapper.LogMapper.selectAllByTable - ==>  Preparing: select * from t_log_20220902;
2024-05-30 21:43:28.808 [main] DEBUG c.r.mybatis.mapper.LogMapper.selectAllByTable - ==> Parameters:
2024-05-30 21:43:28.843 [main] DEBUG c.r.mybatis.mapper.LogMapper.selectAllByTable - <==      Total: 2

#{}
2024-05-30 21:44:10.072 [main] DEBUG c.r.mybatis.mapper.LogMapper.selectAllByTable - ==>  Preparing: select * from t_log_?;
2024-05-30 21:44:10.105 [main] DEBUG c.r.mybatis.mapper.LogMapper.selectAllByTable - ==> Parameters: 20220902(String)

org.apache.ibatis.exceptions.PersistenceException:
### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''20220902'' at line 1
### The error may exist in LogMapper.xml
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: select * from t_log_?;
### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''20220902'' at line 1



3. 批量删除：一次删除多条记录
批量删除的SQL语句有两种写法：
第一种：or: delete form t_car where id =1 or id = 2 or id = 3
第二种：delete from t_car where id in(1,2,3)
# {}
2024-05-30 22:03:54.731 [main] DEBUG com.rainbowsea.mybatis.mapper.CarMapper.deleteBath - ==>  Preparing: delete from t_car where id in(?)
2024-05-30 22:03:54.761 [main] DEBUG com.rainbowsea.mybatis.mapper.CarMapper.deleteBath - ==> Parameters: 125,126,127(String)

org.apache.ibatis.exceptions.PersistenceException:
### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: '125,126,127'
### The error may exist in CarMapper.xml
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: delete from t_car where id in(?)
### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Truncated incorrect DOUBLE value: '125,126,127'

${}
2024-05-30 22:05:09.389 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3382f8ae]
2024-05-30 22:05:09.392 [main] DEBUG com.rainbowsea.mybatis.mapper.CarMapper.deleteBath - ==>  Preparing: delete from t_car where id in(125,126,127)
2024-05-30 22:05:09.425 [main] DEBUG com.rainbowsea.mybatis.mapper.CarMapper.deleteBath - ==> Parameters:
2024-05-30 22:05:09.434 [main] DEBUG com.rainbowsea.mybatis.mapper.CarMapper.deleteBath - <==    Updates: 3
应该采用${}的方式

4.模糊查询：like
需求:根据汽车品牌进行模糊查询
    select * from t_car where brand like '%奔驰%'
    select * from t_car where brand like '%比亚迪%'
select
id,
car_num as carNum,
brand,
guide_price as guidePrice,
produce_time as produceTime,
car_type as carType
from t_car
where brand like '%?%'
ps.setString(1)


#{}
2024-05-30 22:13:43.213 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectByBrandLike - ==>  Preparing: select id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car where brand like '%?%'

org.apache.ibatis.exceptions.PersistenceException:
### Error querying database.  Cause: org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property='brand', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: Parameter index out of range (1 > number of parameters, which is 0).
### The error may exist in CarMapper.xml
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: select    id,    car_num as carNum,    brand,    guide_price as guidePrice,    produce_time as produceTime,    car_type as carType   from t_car   where brand like '%?%'
### Cause: org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property='brand', mode=IN, javaType=class java.lang.Object, jdbcType=null, num

${}
2024-05-30 22:12:55.472 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectByBrandLike - ==>  Preparing: select id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car where brand like '%美瑞%'
2024-05-30 22:12:55.509 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectByBrandLike - ==> Parameters:
2024-05-30 22:12:55.543 [main] DEBUG c.r.mybatis.mapper.CarMapper.selectByBrandLike - <==      Total: 6
方案一：'%${brand}%'
方案二: count函数，这个是mysql数据库当中的一个函数，专门进行字符串拼接的
        concat('%',#{brand},'%')
方案三: concat('%','${brand}','%')
方案四: "%"#{brand}"%"



5.关于 myBatis 中的别名机制:
<!--
        type: 指定給哪个类型起别名
        alias: 指定别名
        注意：别名不区分大小写
        <namespace = “”>接口，一定要为全限定类名(带有包名)，不可以用别名机制
-->
<!--            <typeAlias type="com.rainbowsea.mybatis.pojo.Car" alias="aaa"></typeAlias>-->
<!--            <typeAlias type="com.rainbowsea.mybatis.pojo.Log" alias="bbb"></typeAlias>-->

<!--            省略alias 属性之后，别名就是类的简名了，比如:com.rainbowsea.mybatis.pojo.Car 的别名就是 Car/CAR/cAR
 不区分大小写的。-->
<!--            <typeAlias type="com.rainbowsea.mybatis.pojo.Car"></typeAlias>-->
<!--            <typeAlias type="com.rainbowsea.mybatis.pojo.Log"></typeAlias>-->


            <!--使用 <package>	还可以将这个包下的所有的类的全部自动起别名，别名就是简名，不区分大小写-->
           <package name="com.rainbowsea.mybatis.pojo"/>
        所有的别名不区分大小写
        namespace 不能使用别名机制
同时需要按照一定的顺序放置，放到指定的顺序当中去

6. mybatis-config.xml 文件中的mappers标签
   <mapper resource="CarMapper.xml"></mapper>  要求类的根路径下必须有:CarMapper.xml
   <mapper resource="LogMapper.xml"></mapper> 要求在d:/下有CarMapper.xml 文件，同时注意是三个斜杆
   <mapper url="file:///d:/CarMapper.xml"></mapper>
   <mapper class="全限定接口名.带有包名，与对应的接口的路径一定要保持一致"></mapper>
   
<mapper 标签的属性可以有三个>
> resource: 这种方式是从类的根路径下，开始查找资源，采用这种方式，你的配置文件放到类的路径当中才行。
> url:这种方式是一种绝对路径的方式，这种方式不要求配置文件必须放到类的路径当中，哪里都行，只要提供了一个绝对路径就行，
> 这种方式使用极少，因为移植性太差了。
> class: 这位置提供的是mapper接口的全限定接口名，必须带有包名（就是要一定要和对应接口的路径是一致的，一致的，一致的）
```xml
        <mapper class="com.rainbowsea.mybatis.mapper.CarMapper"><mapper>
        如果你class指定是:com.rainbowsea.mybatis.mapper.CarMapper
        那么mybatis框架会自动去com/rainbowsea/mybatis/mapper/CarMapper/的目录下找，注意是 / 
注意：也就是说，如果你采用这种方式，那么你必须保证:CarMapper.xml文件和CarMapper接口必须在同一个目录下，并且名字也是一致的

CarMapper接口——》CarMapper.xml
LogMapper接口——> LogMapper.xml
    <!-- 这里也是可以使用 package 包名扫描，但是同样的：对应接口路径要一致，接口名一致-->
    <package name="com.rainbowsea.mybatis.mapper"></package>
```
提醒：
在IDEA的resources 目录下新建多重目录的话，必须是这样创建:
com/rianbowsea/mybatis/mapper/
不然是
com.rianbowsea.mybatis.mapper 这是建包了


获取主键




