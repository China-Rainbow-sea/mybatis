package com.rianbowsea.bank.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class SqlSessionUtil {
    // 工具类的构造方法一般都是私有话化的
    // 工具类中所有的方法都是静态的，直接类名即可调用，不需要 new 对象
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


    // 全局的，服务器级别的，一个服务器当中定义一个即可
    private static ThreadLocal<SqlSession> local = new ThreadLocal<>();

    /**
     * 获取会话对象
     *
     * @return SqlSession
     */
    public static SqlSession openSession() {
        // 先从 ThreadLocal 当中获取，获取到 SqlSession 对象
        SqlSession sqlSession = local.get();

        if (null == sqlSession) {
            // ThreadLocat 没有就， 创建一个
            sqlSession = sessionFactory.openSession();
            // 同时将其设置到 ThreadLocal容器当中,将SqlSession对象绑定到当前线程上
            local.set(sqlSession);
        }

        return sqlSession;
    }


    /**
     * 关闭SqlSession 对象（从当前线程中移除SqlSession 对象）
     * @param sqlSession
     */
    public static void close(SqlSession sqlSession) {
        if(sqlSession != null) {
            // 1.先将其关闭
            sqlSession.close();
            // 2. 再将其当前线程移除ThreadLocal 当前线程外面，防止被其他线程拿到整个没用的线程
            local.remove();
            /*
            注意：移除SqlSession 对象和当前线程的绑定关系
            因为Tomcat 服务器是支持线程池的，也就是说，用过的先吃对象t1，可能下一I此还会使用整个t1(已经关闭，没用的)线程。
             */
        }
    }
}
