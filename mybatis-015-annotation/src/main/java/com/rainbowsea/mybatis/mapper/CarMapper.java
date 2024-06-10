package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CarMapper {


    @Select("select * from t_car where id = #{id}")
    Car selectById(Long id);








    @Delete("delete from t_car where id = #{id}")
    int deleteById(Long id);






    @Update("update t_car set car_num=#{carNum},brand=#{brand},guide_price=#{guidePrice},produce_time=#{produceTime}," +
            "car_type=#{carType} where id=#{id}")
    int update(Car car);









    @Insert("insert into t_car values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})")
    int insert(Car car);





}
