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


> java 程序中使用POJO类给SQL语句的占位符传值:
>  // 封装数据
Car car = new Car(null, "333", "比亚迪泰", 30.0, "2020-11-11", "新能源");
>  注意：占位符#{}, 大括号里面写：pojo类的属性名
> insert into t_car(id,car_num,bread,guider_prive,produce_time,car_type)
>  values(null,#{xyz},#{brand},#{guiderPrice},#{produceTime},#{carType})

出现了什么问题呢
>  There is no getter for property named 'xyz' in 'class com.rainbowsea.mybatis.pojo.Car' 
> mybatis 去找，Car类中的getXyz()方法去了，没找到，报错了。
> ```java
> 怎么解决的？
> 可以在Car 类中提供一个getXyz()方法，这样问题就解决了。
    public String getXyz() {
        return carNum;
    }
 
```

> 通过这个测试，得出一个结论：
> 严格意义上来说，如果使用POJO对象传递值的话，#{}这个大括号中i给你到底写什么？
> 写的是对应的属性的 get方法的方法名去掉 get,然后将剩下的单词字母小写，然后放进去。
> 例如：getUsername() ---> #{username}
> 例如: getEmail() ---> #{email}
> ...

也就是说MyBatis在底层，传值的时候，先要获取值，怎么获取的？
    调用了pojo对象的get方法，例如：car.getCarNum(); car.getCarType(), car.getBreand() 方法



3.delete
* 需求：根据id删除数据，将id=4的数据删除

实现:
        int count = sqlSession.delete("deleteById",59);
    <delete id="deleteById">
		delete from t_car where id = #{adssffasd}
	</delete>

注意: 如果占位符只有一个，那么#{}的大括号里可以随意，但是最好见名知意


4. update
    需求: 根据id 修改某条记录
实现:
	<update id="updateById">
		update t_car set car_num = #{carNum}, brand=#{brand}, guide_price=#{guiderPrice},produce_time=#{produceTime},
		                 car_type=#{carType} where id =#{id}
	</update>

  Car car = new Car(14L, "999", "凯美瑞", 30.3, "1999-11-10", "燃油车");
  // 执行SQL语句
  int count = sqlSession.update("updateById", car);


5.select （查一个，根据主键查询的话，返回的结果一定是一个）
<!--resultType="指定结果集要封装的Java对象类型告诉mybatis，要全限定类名"-->
	<select id="selectById" resultType="com.rainbowsea.mybatis.pojo.Car">
		select * from t_car where id= #{id}
	</select>

    // JDBC中间ResultSet，接下来是mybatis应该从ResultSet中取出数据，封装Java对象
        Object car = sqlSession.selectOne("selectById",1);

需要特别注意的是：
select 标签中给的resultType属性，这个属性用来告诉mybatis，查询结果封装什么类型的Java对象
resultType通常写的是：全限定类名


Car{id=1, carNum='null', brand='宝马520Li', guiderPrice=null, produceTime='null', carType='null'}
输出结果有点不对劲：id和 brand属性有值，其他属性为null, 解决方式用：as 别名
car_num,guide_price,produce_time,car_type 这是查询结果的别名
这些别名和Car类中的属性名对不上
Car 类的属性名
carNum,guiderPrice,produceTime,carType

那么这个问题怎么解决呢：
 > select 语句查询的时候，查询结果集的别民是可以使用as关键字起别名的

6. select (查所有的)

	<select id="selectAll" resultType="com.rainbowsea.mybatis.pojo.Car">
		select
		       id,car_num as carNum, brand, guide_price as guiderPrice,
			   produce_time as produceTime,
			   car_type as carType
		from
		       t_car

	</select>

  List<Car> cars = sqlSession.selectList("selectAll");
注意: resultType 还是指定封装的结果集的类型，不是指定List类型，是指定List集合中元素的类型
selectList 方法，mybatis通过这个方法就可以得知你需要一个List集合，它会自动给你返回一个List集合

7.在sql mapper.xml 文件当中有一个namespace,这个属性是用来指定命名空间的，用来防止id重复
怎么用？
<mapper namespace="rainbowseaaaaa">

	<!--	insert语句，id是这个条SQL语句的唯一标识，这个id就代表了这条SQL语句 -->
	<!--	<insert id="insertCar">-->
	<!--		insert into t_car(id,car_num,brand,guide_price,produce_time,car_type)-->
	<!--		values (null,#{carNum},#{brand},#{guiderPrice},#{produceTime},#{carType})-->
	<!--  map.get("fdsd") 找，结果找不到 = null-->
	<!--	</insert> -->

	<!--	 map.get("fdsd") 找，结果找不到 = null-->
	<insert id="insertCar">
		insert into t_car(id,car_num,brand,guide_price,produce_time,car_type)
		values (null,#{carNum},#{brand},#{guiderPrice},#{produceTime},#{carType})
	</insert>

在Java程序当中的写法:
   // 执行SQL语句
        List<Car> cars = sqlSession.selectList("rainbowseaaaaa.selectAll");
实际上，本质上，mybatis中的sqlId的完整写法: namespace.id ，注意，之后都这么写了，这是完整正确的写法。