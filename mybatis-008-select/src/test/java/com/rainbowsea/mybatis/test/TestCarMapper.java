package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestCarMapper {

    @Test
    public void testSelectTotal() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Long total = mapper.selectTotal();
        System.out.println("总记录条数: " + total);
        sqlSession.close();
    }


    @Test
    public void testSelectAllByMapUnderscoreToCamelCase() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllByMapUnderscoreToCamelCase();
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }


    @Test
    public void testSelectAllByResultMap() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllByResultMap();
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }


    @Test
    public void testSelectAllRetMap() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Map<Long, Map<String, Object>> cars = mapper.selectAllRetMap();
        System.out.println(cars);
        sqlSession.close();
    }


    @Test
    public void testSelectAllRetListMap() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Map<String, Object>> cars = mapper.selectAllRetListMap();
        cars.forEach(car -> {
            System.out.println(car);
        });
    }


    @Test
    public void testSelectByIdRetMap() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"),
                "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();

        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Map<String, Object> cars = mapper.selectByIdRetMap(131L);
        System.out.println(cars);
        sqlSession.close();

    }


    @Test
    public void testSelectById2() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"),
                "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();

        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectById2(131L);
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();

    }


    @Test
    public void testSelectByBrandLike() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectByBrandLike("小米");
        // org.apache.ibatis.exceptions.TooManyResultsException:
        // 什么意思？你期望的结果是返回一条记录，但是实际的SQL语句在执行的时候，返回的记录条数不是一条，是多条
        System.out.println(car);
        sqlSession.close();
    }


    @Test
    public void testSelectAll() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"),
                "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();

    }

    @Test
    public void testSelectById() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"),
                "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();

        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(131L);
        System.out.println(car);
        sqlSession.close();

    }
}
