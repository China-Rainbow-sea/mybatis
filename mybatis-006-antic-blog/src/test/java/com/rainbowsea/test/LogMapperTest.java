package com.rainbowsea.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.mapper.LogMapper;
import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.pojo.Log;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class LogMapperTest {
    @Test
    public void testInsertCarUseGeneratedKeys() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, "985", "凯美瑞", 3.0, "2000-10-10", "新能源");

        int count = mapper.insertCarUserGeneratedKey(car);
        sqlSession.commit();
        sqlSession.close();

        System.out.println(car);
    }





    @Test
    public void testSelectAllByTable() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        LogMapper mapper = sqlSession.getMapper(LogMapper.class);
        List<Log> logs = mapper.selectAllByTable("20220902");
        logs.forEach(log -> {
            System.out.println(log);
        });
    }
}
