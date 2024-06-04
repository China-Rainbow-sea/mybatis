package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.StudentMapper;
import com.rainbowsea.mybatis.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class StudentMapperTest{

    @Test
    public void testSelectByIdStep1() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdStep1(5);
        //System.out.println(student);
        // 只需要看学生的名字
        System.out.println(student.getSname());
        System.out.println(student.getClazz().getCname());
        sqlSession.close();
    }


    @Test
    public void testSelectByIdAssociation() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdAssociation(1);
        System.out.println(student);
        sqlSession.close();

    }


    @Test
    public void testSelectById() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1);
        System.out.println(student);
        sqlSession.close();

    }
}
