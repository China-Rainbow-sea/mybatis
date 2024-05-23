package com.rainbowsea.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyBatisIntroductionTest {

    public static void main(String[] args) throws IOException {
        // 1. 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2. 创建SqlSessionFactory对象
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 3. 创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        // 4. 执行sql
        int count = sqlSession.insert("insertCar"); // 这个"insertCar"必须是sql的id
        System.out.println("插入几条数据：" + count);
        // 5. 提交（mybatis默认采用的事务管理器是JDBC，默认是不提交的，需要手动提交。）
        //sqlSession.commit();
        // 6. 关闭资源（只关闭是不会提交的）
        sqlSession.close();

    }

    public static void main2(String[] args) throws IOException {

        // 获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        //获取到SqlSessionFactory 对象
        //InputStream inputStream = new FileInputStream("mybatis-config.xml文件的路径");

        // 注意是：import org.apache.ibatis.io.Resources; mybatis 下面的工具类
        //InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");  // Resources
        // .getResourceAsStream()默认是从类路径下开始查找资源。


        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");  // Resources

        //InputStream stream = new FileInputStream("E:\\mybatis-config.xml");
        //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");  // 使用类加载器
        //进行加载一个IO流出来。
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);// 一般情况下都是一个数据库对应一个SqlSessionFactory对象


        // 获取到SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 如果使用的事务管理器是JDBC的话，底层实际上会执行：conn.setAutoCommit(false)不会自动提交 ；默认是 true 就是会自动提交


        // 执行sql语句
        int count = sqlSession.insert("insertCar");  // 根据对应XxxMapper.xml 当中所编写的SQL语句的id 进行定位

        System.out.println("插入了几条记录 " + count);

        // 手动提交一下
        //sqlSession.commit();  // 底层实际上还是会执行conn.commit();

    }
}
