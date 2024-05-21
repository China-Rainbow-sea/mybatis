package com.rainbowsea.mybatis.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class SqlSessionUtil {
    // 工具类的构造方法一般都是私有话化的
    // 工具类中所有的方法都是静态的，直接次啊用雷曼即可调用，不需要 new 对象
    // 为了防止new对象，构造方法私有化。

    private SqlSessionUtil() {

    }



    private static SqlSessionFactory sessionFactory = null;

    // 静态代码块，类加载时执行
    // SqlSessionUtil 工具类在进行第一次加载的时候，解析mybatis-config.xml 文件，创建SqlSessionFactory对象。
    static {
        // 获取到  SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 获取到SqlSessionFactory 对象
        // SQlsessionFactory对象，一个SqlSessionFactory对应一个 environment, 一个environment通常是一个数据库
        try {
            sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取会话对象
     * @return SqlSession
     */
    public static SqlSession openSession() {
        // 获取到 SqlSession 对象
        SqlSession sqlSession = sessionFactory.openSession();
        return sqlSession;
    }
}
