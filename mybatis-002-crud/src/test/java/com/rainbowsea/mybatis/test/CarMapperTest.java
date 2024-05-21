package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CarMapperTest {


    @Test
    public void testInsertCar2() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 前端传过来数据了
        // 这个对象我们先使用Map集合进行数据的封装
        Map<String,Object> map = new HashMap<>();

        map.put("carNum","111");
        map.put("bread","比亚迪汉");
        map.put("guiderPrice",10.0);
        map.put("produceTime","2020-11-11");
        map.put("carType","电车");

        // 执行sql语句
        // insert方法的参数:
        // 第一个参数:sqlId；从CarMapper.xml 文件中复制
        // 第二个参数: 封装数据的对象
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testInsertCar() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 前端传过来数据了
        // 这个对象我们先使用Map集合进行数据的封装
        Map<String,Object> map = new HashMap<>();

        map.put("k1","111");
        map.put("k2","比亚迪汉");
        map.put("k3",10.0);
        map.put("k4","2020-11-11");
        map.put("k5","电车");

        // 执行sql语句
        // insert方法的参数:
        // 第一个参数:sqlId；从CarMapper.xml 文件中复制
        // 第二个参数: 封装数据的对象
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }
}
