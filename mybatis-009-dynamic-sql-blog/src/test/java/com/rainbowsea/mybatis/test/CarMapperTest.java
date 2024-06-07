package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectById2() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById2(118L);
        System.out.println(car);
        sqlSession.close();
    }



    @Test
    public void testDeleteByIds2() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Long[] ids = {136L, 137L};
        mapper.deleteByIds2(ids);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testInsertBath() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        Car car1 = new Car(null, "1201", "玛莎拉蒂1", 30.0, "2020-12", "燃油车");
        Car car2 = new Car(null, "1202", "玛莎拉蒂2", 30.0, "2020-12", "燃油车");
        Car car3 = new Car(null, "1203", "玛莎拉蒂3", 30.0, "2020-12", "燃油车");
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        mapper.insertBath(cars);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testDeleteByIds() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Long[] ids = {128L, 132L};
        mapper.deleteByIds(ids);
        sqlSession.commit();
        sqlSession.close();
    }


    /**
     * 只有一个分支会被选择！！！！
     */
    @Test
    public void testSelectByChoose() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 三个条件不为空
        List<Car> cars = mapper.selectByChoose("丰田霸道", 3.00, "燃油车");
        // 第一条件为空
        List<Car> cars2 = mapper.selectByChoose("", 3.00, "燃油车");
        // 两个条件为Null
        List<Car> cars3 = mapper.selectByChoose("", null, "燃油车");
        // 全部为空，执行选择 otherwise 标签当中的信息
        List<Car> cars4 = mapper.selectByChoose("", null, "");
        cars4.forEach(car -> {
            System.out.println(car);
        });

        sqlSession.close();
    }


    /*
       * 主要使用在update语句当中，用来生成set关键字，同时去掉最后多余的“,”
   比如我们只更新提交的不为空的字段，如果提交的数据是空或者""，那么这个字段我们将不更新。*/
    @Test
    public void testUpdateSet() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(128L, null, "丰田霸道", null, null, "燃油车");
        mapper.updateSet(car);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testSelectByMultiConditionWithTrim() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiConditionWithTrim("", null, "");
        List<Car> cars2 = mapper.selectByMultiConditionWithTrim("小米", null, "");

        cars.forEach(car -> {
            System.out.println(car);
        });


        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithWhere() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 假设三个条件都不为null
        List<Car> cars = mapper.selectByMultiConditionWithWhere("小米", 21.00, "新能源");

        // 如果第一个条件是空，其他的不为空
        List<Car> cars2 = mapper.selectByMultiConditionWithWhere("", 21.00, "新能源");


        // 假设第一个条件不是空，后两个条件为空
        List<Car> cars3 = mapper.selectByMultiConditionWithWhere("小米", null, "");

        cars3.forEach(car -> {
            System.out.println(car);
        });


        sqlSession.close();
    }


    @Test
    public void testSelectByMultiCondition() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 假设三个条件都不为null
        //List<Car> cars = mapper.selectByMultiCondition("小米",21.00,"新能源");
        // 假设三个条件都是空
        //List<Car> cars = mapper.selectByMultiCondition("",null,"");

        // 假设后两个条件不为空，第一个条件为空
        //List<Car> cars = mapper.selectByMultiCondition("",21.00,"新能源");

        // 假设第一个条件不是空，后两个条件为空
        List<Car> cars = mapper.selectByMultiCondition("小米", null, "");

        cars.forEach(car -> {
            System.out.println(car);
        });


        sqlSession.close();

    }
}
