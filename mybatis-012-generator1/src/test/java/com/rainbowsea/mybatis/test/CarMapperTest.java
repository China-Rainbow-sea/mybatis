package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.pojo.CarExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {
    @Test
    public void testDeleteByPrimaryKey() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 执行查询
        // 1. 查询一个
        Car car = mapper.selectByPrimaryKey(118L);
        System.out.println(car);

        // 2. 查询所有（selectByExample 根据条件查询，如果条件是null表示没有条件）
        List<Car> cars = mapper.selectByExample(null);
        cars.forEach(car1 -> {
            System.out.println(car1);
        });

        // 3. 按照条件进行查询
        // 封装条件，通过CarExample 对象来封装查询条件
        CarExample carExample = new CarExample();
        // 调用carExample.createCriteria()方法来创建查询条件
        carExample.createCriteria()
                .andBrandLike("小米")
                .andGuidePriceGreaterThan(new BigDecimal(30.0));
        // 添加 or
        carExample.or().andCarTypeEqualTo("燃油车");

        // 执行查询
        List<Car> cars2 = mapper.selectByExample(carExample);
        cars2.forEach(car2->{
            System.out.println(car2);
        });


        sqlSession.close();

    }
}
