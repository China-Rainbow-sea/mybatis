package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsert() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "8654", "凯美瑞", 3.0, "2000-10-10", "新能源");
        int count = mapper.insert(car);
        System.out.println(count);
        sqlSession.commit();  // 提交
        sqlSession.close();
    }



    @Test
    public void testDeleteById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.deleteById(119L);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();

    }


    @Test
    public void testUpdate() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(118L, "999", "凯美瑞", 3.0, "2000-10-10", "新能源");
        int count = mapper.update(car);
        sqlSession.commit();
        sqlSession.close();

    }


    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(118L);
        System.out.println(car);

    }


    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);  // 保持一致

        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {System.out.println(car);});

    }
}
