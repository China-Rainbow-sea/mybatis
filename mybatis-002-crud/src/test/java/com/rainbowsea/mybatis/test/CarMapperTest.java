package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMapperTest {

    @Test
    public void testNamespace() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 执行SQL语句
        // 正确的完整写法:namespace.id
        List<Car> cars = sqlSession.selectList("rainbowsea2.selectAll");

        // 遍历
        cars.forEach(car -> {
            System.out.println(car);
        });

        //sqlSession.commit();  查询不用提交,没有事务问题
        sqlSession.close();

    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 执行SQL语句
        List<Car> cars = sqlSession.selectList("selectAll");

        // 遍历
        cars.forEach(car -> {
            System.out.println(car);
        });

        //sqlSession.commit();  查询不用提交,没有事务问题
        sqlSession.close();
    }


    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 执行SQL语句，查询，根据id查询，返回结果一定是一条
        // mybatis底层执行select语句之后，一定会会返回一条结果集对象，ResultSet
        // JDBC中间ResultSet，接下来是mybatis应该从ResultSet中取出数据，封装Java对象
        Object car = sqlSession.selectOne("selectById",1);
        System.out.println(car);

        //sqlSession.commit();  仅仅只是查询，并不需要提交
        sqlSession.close();
    }


    @Test
    public void testUpdateById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        Car car = new Car(46L, "999", "凯美瑞", 30.3, "1999-11-10", "燃油车");
        // 执行SQL语句
        int count = sqlSession.update("updateById", car);
        System.out.println(count);


        sqlSession.commit();
        sqlSession.close();


    }


    @Test
    public void testDeleteById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 执行SQL语句
        sqlSession.delete("deleteById",44); // 如果只要一个值的时候，就不需要对应上的的 Object 类型了
        sqlSession.commit(); // 提交
        sqlSession.close(); // 关闭
    }


    @Test
    public void testInsertCarByPOJO() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 封装数据
        Car car = new Car(null, "333", "比亚迪泰", 30.0, "2020-11-11", "新能源");

        // 执行SQL
        int count = sqlSession.insert("insertCar", car); // ORM       // 对应XxxMapper.xml 上的id
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();

    }


    @Test
    public void testInsertCar2() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 前端传过来数据了
        // 这个对象我们先使用Map集合进行数据的封装
        Map<String,Object> map = new HashMap<>();

        map.put("carNum","111");
        map.put("brand","比亚迪汉");
        map.put("guiderPrice",10.0);
        map.put("produceTime","2020-11-11");
        map.put("carType","电车");

        // 执行sql语句
        // insert方法的参数:
        // 第一个参数:sqlId；从CarMapper.xml 文件中复制
        // 第二个参数: 封装数据的对象
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testInsertCar() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 前端传过来数据了
        // 这个对象我们先使用Map集合进行数据的封装
        Map<String,Object> map = new HashMap<>();

        map.put("k1","111");
        map.put("k2","比亚迪汉");
        map.put("k3",10.0);
        map.put("k4","2020-11-11");
        map.put("k5","电车");

        // 执行sql语句
        // insert方法的参数:
        // 第一个参数:sqlId；从CarMapper.xml 文件中复制
        // 第二个参数: 封装数据的对象
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }
}
