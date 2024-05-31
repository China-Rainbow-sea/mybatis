package com.rianbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.StudentMapper;
import com.rainbowsea.mybatis.pojo.Student;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMapperTest {

    // 多个参数

    /**
     * 根据 name 和 sex 查询 Student 信息
     * 如果是多个参数，mybatis 框架底层是怎么做的呢？
     *  mybatis 框架会自动创建一个map集合，并且map集合是以
     *  这种方式存储参数的
     *  map.put("arg0",name)
     *  map.put("arg1",sex)
     *  map.put("param1",name)
     *  map.put("param2",sex)
     *
     *
     */
    @Test
    public void TestSelectByNameAndSex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // mapper 实际上指向了代理对象
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        // mapper是代理对象
        // selectByNameAndSex是代理方法
        List<Student> students = mapper.selectByNameAndSex("李华", '男');
        students.forEach(student -> {
            System.out.println(student);
        });

        sqlSession.close();
    }

    @Test
    public void TestSelectByNameAndSex2(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex2("李华", '男');
        students.forEach(student -> {
            System.out.println(student);
        });

        sqlSession.close();
    }


    // 单个参数

    @Test
    public void testInsertStudentByPOJO() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = simpleDateFormat.parse("2020-10-10");
        Student student = new Student(null, "小红", 18, 166.0, birth, '女');
        mapper.insertStudentByPOJO(student);
        sqlSession.commit();
        sqlSession.close();

    }


    @Test
    public void testInsertStudentByMap() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("姓名","赵六");
        map.put("年龄",20);
        map.put("身高",1.83);
        map.put("性别",'男');
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = simpleDateFormat.parse("2020-09-09");
        map.put("生日",birth);
        mapper.insertStudentByMap(map);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testSelectBySex() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectBySex('男');
        students.forEach(student ->
        {
            System.out.println(student);
        });

        sqlSession.close();
    }


    //java.util.Date; java.sql.Date 他们在mybatis当中都是为简单类型
    @Test
    public void testSelectByBrith() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = simpleDateFormat.parse("2020-06-06");
        List<Student> students = mapper.selectByBirth(birth);
        students.forEach(student -> {
            System.out.println(student);
        });

        sqlSession.close();
    }

    @Test
    public void testSelectByName() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByName("李华");
        students.forEach(student ->
        {
            System.out.println(student);
        });

        sqlSession.close();
    }


    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectById(1L);

        students.forEach(student -> {
            System.out.println(student);
        });

        sqlSession.close();
    }
}
