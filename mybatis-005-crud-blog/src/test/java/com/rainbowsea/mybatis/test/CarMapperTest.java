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

public class CarMapperTest {
    @Test
    public void testSelectAll() throws IOException {
        // 获取到 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        // 获取到 SalSession 会话，一次会话一个
        SqlSession sqlSession = sessionFactory.openSession();
        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {
            System.out.println(car);
        });

        sqlSession.close();
    }

    @Test
    public void testSelectById() throws IOException {
        // 获取到 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        // 获取到 SalSession 会话，一次会话一个
        SqlSession sqlSession = sessionFactory.openSession();
        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(130L);
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testUpdate() throws IOException {
        // 获取到 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        // 获取到 SalSession 会话，一次会话一个
        SqlSession sqlSession = sessionFactory.openSession();
        Car car = new Car(128L, "999", "小米su7", 21.0, "2024-03-28", "新能源");
        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.update(car);
        sqlSession.commit(); // 提交给数据库
        sqlSession.close();


    }


    @Test
    public void testDeleteById() throws IOException {
        // 获取到 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        // 获取到 SalSession 会话，一次会话一个
        SqlSession sqlSession = sessionFactory.openSession();
        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 删除id为 124的一条记录。
        int count = mapper.deleteById(124L);
        sqlSession.commit(); // 提交给数据库
        sqlSession.close(); // 关闭资源
        System.out.println(count);

    }


    @Test
    public void testInsert() throws IOException {
        // 获取到 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        // 获取到 SalSession 会话，一次会话一个
        SqlSession sqlSession = sessionFactory.openSession();
        Car car = new Car(null, "999", "奥迪", 3.0, "2000-10-10", "新能源");
        // 面向接口编程，获取接口的代理对象，也就是接口的实现类，实现类该接口中的方法
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.insert(car);

        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();


    }
}
