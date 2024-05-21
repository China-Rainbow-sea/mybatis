# 在mybatis当中完成CRUD

1. 什么是CRUD
> C: Create 增
> R: Retrieve 查(检索)
> U: update 改
> D: Delete 删

2. insert
```sql
insert into t_car(id,car_num,brand,guide_price,produce_time,car_type)
values (null,'1003','丰田霸道',30.0,'2000-10-11','燃油车')
```
这样写的问题是？
> 值，显然是写死到配置文件当中了
> 这个实际开发中是不存在的
> 一定是前端form表单提交过来数据，然后将值传给sql语句


例如: JDBC的代码怎么写的？
> String sql = "insert into t_car(id,car_num,brand,guide_produce,car_type) value(null,?,?,?,?,?)"
> ps.setString(1,xxx)
> ps.setString(2,yyy)

在JDBC当中占位符采用的是?,在mybatis当中是什么呢？
> 和？ 等效的写法是: #{}
> 在mybatis当中不能使用 ？ 占位符，必须使用#{} 来代替JDBC当中的 ?
> #{}和JDBC当中的 ？ 是等效的。

java程序中使用map可以给SQL语句的占位符传值
Map<String,Object> map = new HashMap<>();

map.put("k1","111");
map.put("k2","比亚迪汉");
map.put("k3",10.0);
map.put("k4","2020-11-11");
map.put("k5","电车");

insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values (null,#{fdsd},#{k2},#{k3},#{k4},#{k5})
注意：#{这里写什么？写map集合的key,如果key不存在，获取的是null}

