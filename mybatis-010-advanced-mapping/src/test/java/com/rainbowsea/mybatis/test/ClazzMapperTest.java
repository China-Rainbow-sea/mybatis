package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.ClazzMapper;
import com.rainbowsea.mybatis.pojo.Clazz;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class ClazzMapperTest {
    @Test
    public void testSelectByStep1() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByStep1(1000);
        //System.out.println(clazz);
        // 只访问班级名字
        System.out.println(clazz.getCname());

        // 只有用到的时候才会去执行第二步SQL
        System.out.println(clazz.getStus());
        sqlSession.close();
    }



    @Test
    public void testSelectByCollection() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCollection(1000);
        System.out.println(clazz);
        sqlSession.close();
    }
}
