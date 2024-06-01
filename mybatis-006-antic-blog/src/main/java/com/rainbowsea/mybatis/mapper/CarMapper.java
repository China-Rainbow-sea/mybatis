package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;

import java.util.List;

/**
 * 封装汽车相关信息的pojo类，普通的Java类
 */
public interface CarMapper {

    /**
     * 插入 Car 信息，并且使用生成的主键值
     * @param car
     * @return
     */
    int insertCarUserGeneratedKey(Car car);




    /**
     * 根据汽车类型获取汽车信息
     * @param carType
     * @return
     */
    List<Car> selectByCarType(String carType);


    /**
     * 查询所有的汽车信息，然后通过 asc 升序，desc 降序
     * @param ascOrDesc
     * @return
     */
    List<Car> selectAllByAscOrDesc(String ascOrDesc);


    /**
     * 批量删除，根据id
     * @param ids
     * @return
     */
    int deleteBath(String ids);


    /**
     * 根据汽车品牌进行模糊查询
     * @param brand
     * @return
     */
    List<Car> selectByBrandLike(String brand);



}
