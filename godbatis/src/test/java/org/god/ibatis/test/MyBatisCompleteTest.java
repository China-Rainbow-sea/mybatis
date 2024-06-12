package org.god.ibatis.test;

import org.god.ibatis.core.SqlSession;
import org.god.ibatis.core.SqlSessionFactory;
import org.god.ibatis.core.SqlSessionFactoryBuilder;
import org.god.ibatis.utils.Resources;

public class MyBatisCompleteTest {
    public static void main(String[] args) {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));

        // 开启会话（底层会话开启事务）
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 执行SQL语句，处理相关事务
        //int count = sqlSession.insert("insertCar");
        //System.out.println(count);

        // 执行到这里，没有发生任何异常，提交事务，终止事务
        sqlSession.commit();
    }
}
