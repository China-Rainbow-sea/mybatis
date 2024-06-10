package com.rainbowsea.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rainbowsea.mybatis.mapper.CarMapper;
import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class CarMapperTest {


    @Test
    public void testSelectAll() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 一定一定一定要注意，在执行DQL语句之前，开启分页功能
        int pageNum = 0;
        int pageSize = 3;

        PageHelper.startPage(pageNum, pageSize);

        List<Car> cars = mapper.selectAll();

        // 封装分页信息对象new PageInfo()
        // PageInfo 对象是PageHelper 插件提供的，用来封装分页相关的信息的对象
        PageInfo<Car> carPageInfo = new PageInfo<>();
        System.out.println(carPageInfo);

        sqlSession.close();

        // PageInfo{pageNum=0, pageSize=0, size=0, startRow=0, endRow=0, total=0, pages=0, list=null,
        // prePage=0, nextPage=0, isFirstPage=false, isLastPage=false, hasPreviousPage=false, hasNextPage=false,
        // navigatePages=0, navigateFirstPage=0, navigateLastPage=0, navigatepageNums=null}
    }


    @Test
    public void testSelectByPage() throws IOException {
        // 获取每页显示的记录条数
        int pageSize = 3;
        // 显示第几页：页码
        int pageNum = 1;
        // 计算开始下标
        int startIndex = (pageNum - 1) * pageSize;

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatis");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        List<Car> cars = mapper.selectByPage(startIndex, pageSize);
        cars.forEach(car -> {
            System.out.println(car);
        });

        sqlSession.close();


    }
}
