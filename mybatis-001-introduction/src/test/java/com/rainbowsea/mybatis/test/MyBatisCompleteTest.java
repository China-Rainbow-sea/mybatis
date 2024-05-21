package com.rainbowsea.mybatis.test;

import com.ranbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class MyBatisCompleteTest {
    public static void main(String[] args) {
        // 获取到SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSession sqlSession = null;

        try {
            // 获取到SqlSessionFactoryBuilder 一个数据库对应一个 SqlSessionFactoryBuilder
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"));

            // 获取到SqlSession对象
            sqlSession = sqlSessionFactory.openSession();


            sqlSession.insert("insertCar");// 对应 执行SQL语句对应的数据表的 XxxMapper.xml当中，对应SQL语句的id

            // 执行到这里，提交事务，终止事务
            sqlSession.commit();
        } catch (IOException e) {
            if(sqlSession != null) {  // 上面可能 sqlSession 还是为空
                // 回滚事务
                sqlSession.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if(sqlSession != null) {

                // 关闭会话
                sqlSession.close();
            }
        }




    }


    @Test
    public void testInserCarByUtil() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.insert("insertCar");// 对应XxxMapper的SQL语句的 id

        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }
}
