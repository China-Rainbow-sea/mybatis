package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Car;
import com.rainbowsea.mybatis.pojo.Clazz;

public interface CarMapper {

    int insertClazz(Clazz clazz);

    Car selectById(Long id);
}
