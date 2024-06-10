package com.rainbowsea.mybatis.test;


import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.pojo.Clazz;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class CarMapperTest {

    @Test
    public void testSelectById4() throws IOException {
        // 这里只有一个SqlSessionFactory 对象，二级缓存对应的就是SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);

        // 这行代码执行结束之后，时间上数据缓存到一级缓存当中了，(sqlSession是一级缓存)
        Car car = mapper1.selectById(118L);
        System.out.println(car);

        // 如果这里不关闭sqlSession对象的话，二级缓存中还是没有数据的
        // 如果执行了这行代码，sqlSession1的一级缓存中的数据会放到二级缓存当中
        sqlSession1.close();



        // 这行代码执行结束之后，实际上数据会缓存到一级缓存当中。（sqlSession2 是一级缓存）
        Car car1 = mapper2.selectById(118L);
        System.out.println(car1);

        // 程序执行到这里的时候，会有SqlSession1这个一级缓存中的数据写入到二级缓存当中
        // sqlSession1.close()

        // 程序执行到这里的时候，会将sqlSession2这个一级缓存中的数据写入到二级缓存当中
        sqlSession2.close();
    }



    /**
     * 思考：什么时候不走缓存？
     * sqlsession 对象不是同一个，肯定不走缓存
     * 查询条件不一样，肯定不走缓存
     * <p>
     * 思考什么时候一级缓存失败？
     * 第一次DQL和第二次DQL之间你做了一下两件事的任意一种，都会让一级缓存清空
     * 1. 执行了 sqlSession.clearCache()方法，这是手动情况缓存
     * 2. 执行了INSERT 或 DELETE 或UPDATE语句，不管你是操作那张表，都会清空一级缓存
     *
     * @throws IOException
     */
    @Test
    public void testSelectById3() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(118L);
        System.out.println(car);

        // 手动清空一级缓存
        //sqlSession.clearCache();
// 在这里执行 insert或者 delete 或者 update 中的任意一个语句，并且和表没有关系
        CarMapper mapper2 = sqlSession.getMapper(CarMapper.class);
        mapper2.insertClazz(new Clazz(2000,"高三三班"));

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(118L);
        System.out.println(car1);

        sqlSession.close();
    }


    @Test
    public void testSelectById2() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession1 = sqlSessionFactory.openSession();


        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(118L);
        System.out.println(car);

        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(118L);
        System.out.println(car1);
    }


    @Test
    public void testSelectById() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(118L);
        System.out.println(car);

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(118L);
        System.out.println(car1);
    }


}
