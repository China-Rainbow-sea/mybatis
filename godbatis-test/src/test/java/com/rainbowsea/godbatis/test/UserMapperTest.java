package com.rainbowsea.godbatis.test;

import org.god.ibatis.core.SqlSession;
import org.god.ibatis.core.SqlSessionFactory;
import org.god.ibatis.core.SqlSessionFactoryBuilder;
import org.god.ibatis.pojo.User;
import org.god.ibatis.utils.Resources;
import org.junit.Test;

public class UserMapperTest {

    @Test
    public void testInsertUser() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 执行SQL insert
        User user = new User("99999", "张三", "20");
        int count = sqlSession.insert("user.insert", user);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testSelectOne() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 执行SQL语句
        Object obj = sqlSession.selectOne2("user.selectAll", "666");
        System.out.println(obj);
        sqlSession.close();
    }
}
