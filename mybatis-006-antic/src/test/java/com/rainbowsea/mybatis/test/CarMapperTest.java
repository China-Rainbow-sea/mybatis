package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

public class CarMapperTest {
    @Test
    public void tesSelectByCarType() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // mapper 实际上就是daoImpl 对象
        // 底层不但为CarMapper 接口生成了字节码，并且还new 实现类对象了
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        List<Car> cars = mapper.selectByCarType("新能源");

        cars.forEach(car -> {
            System.out.println(car);
        });

    }

    @Test
    public void testSelectAllByAscOrDesc() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllByAscOrDesc("asc");
        cars.forEach(car -> {
            System.out.println(car);
        });
    }

    @Test
    public void testDeleteBatch() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        int count = mapper.deleteBath("125,126,127");
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByBradLike() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByBrandLike("美瑞");
        cars.forEach(car -> {
            System.out.println(car);
        });

        sqlSession.close();
    }
}
