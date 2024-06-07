package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {

    /**
     * 测试 sql标签，代码片段的运用
     *
     * @param id
     * @return
     */
    Car selectById2(@Param("id") Long id);



    /**
     * 批量删除第二种方式 or
     *
     * @param ids
     * @return
     */
    int deleteByIds2(@Param("ids") Long[] ids);





    /**
     * 批量插入，一次插入多个Car信息
     *
     * @param cars
     * @return
     */
    int insertBath(@Param("cars") List<Car> cars);


    /**
     * 批量删除，foreach 标签
     *
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") Long[] ids);


    /**
     * 使用choose when otherwise 标签
     *
     * @param brand
     * @param guiderPrice
     * @param carType
     * @return
     */
    List<Car> selectByChoose(@Param("brand") String brand, @Param("guidePrice") Double guiderPrice,
                             @Param("carType") String carType);


    /**
     * 使用set 标签
     *
     * @param car
     * @return
     */
    int updateSet(Car car);


    /**
     * 使用 trim 标签，添加删除
     *
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionWithTrim(@Param("brand") String brand, @Param("guidePrice") Double guidePrice,
                                             @Param("carType") String carType);


    /**
     * 使用where标签，让where 子句更加灵活，更加智能
     *
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionWithWhere(@Param("brand") String brand, @Param("guidePrice") Double guidePrice,
                                              @Param("carType") String carType);


    /**
     * 多条件查询
     *
     * @param brand       品牌
     * @param guiderPrice 指导价
     * @param carType     汽车类型
     * @return
     */
    List<Car> selectByMultiCondition(@Param("brand") String brand, @Param("guidePrice") Double guiderPrice,
                                     @Param("carType") String carType);
}
