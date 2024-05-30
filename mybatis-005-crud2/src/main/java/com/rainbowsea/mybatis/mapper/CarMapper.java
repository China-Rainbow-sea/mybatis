package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;

import java.util.List;

/**
 * 封装汽车相关信息的pojo类，普通的Java类
 */
public interface CarMapper {


    /**
     * 新增 Car
     * @param car
     * @return
     */
    int insert(Car car);


    /**
     * 根据id 删除 Car
     * @param id
     * @return
     */
    int deleteById(Long id);


    /**
     * 修改汽车信息
     * @param car
     * @return
     */
    int update(Car car);


    /**
     * 根据id查询汽车信息
     * @param id
     * @return
     */
    Car selectById(Long id);


    /**
     * 获取所有的汽车信息
     * @return
     */
    List<Car> selectAll();

}
