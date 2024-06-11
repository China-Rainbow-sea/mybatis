package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {


    List<Car> selectAll();






    /**
     * 分页查询
     *
     * @param startIndex 起始下标
     * @param pageSize   每页显示的记录条数
     * @return
     */
    List<Car> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
