package com.rainbowsea.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationTest {

    @Test
    public void testProperties() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config3.xml"), "mybatis2");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int count = sqlSession.insert("car.insertCar");
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testPool() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config2.xml"));
        for (int i = 0; i < 4; i++) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("car.insertCar");
        }
    }

    @Test
    public void testDataSourcePoolMaximumActiveConnections() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // SqlSessionFactoryBuilder对象，一个数据库一个，不要频繁创建该对象
        SqlSessionFactory sqlSessionFactory =
                sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"),"mybatis");

        // 通过sqlSessionFactory对象可以开启多个会话
        // 会话1
        for (int i = 0; i < 4; i++) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("car.insertCar");
            // 不要关闭
        }

    }


    // POOLED/ UNPOOLED
    @Test
    public void testDataSource() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // SqlSessionFactoryBuilder对象，一个数据库一个，不要频繁创建该对象
        SqlSessionFactory sqlSessionFactory =
                sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"), "mybatis2");

        // 通过sqlSessionFactory对象可以开启多个会话
        // 会话1
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("car.insertCar");
        sqlSession.commit();
        sqlSession.close();

        // 会话2
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        sqlSession2.insert("car.insertCar");
        sqlSession2.commit();
        sqlSession2.close();
    }


    @Test
    public void testEnvironment() throws IOException {
        // 获取SqlSessionFactory 对象（采用默认的方式）
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 这种方式就是获取默认的环境，因为你并没有指明其对应的环境
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"));

        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行sql语句
        int count = sqlSession.insert("car.insertCar"); // 完整的 namespace.id
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();


        // 这种方式就是通过环境id来使用指定的环境
        SqlSessionFactory sqlSessionFactory2 =
                sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"), "mybatis2");
        SqlSession sqlSession2 = sqlSessionFactory2.openSession();
        // 执行SQL语句
        int count2 = sqlSession2.insert("car.insertCar");
        sqlSession2.commit();
        sqlSession2.close();


    }

}
