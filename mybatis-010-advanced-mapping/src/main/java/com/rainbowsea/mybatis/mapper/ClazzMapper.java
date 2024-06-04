package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Clazz;

public interface ClazzMapper {


    /**
     * 分布查询，第一步，根据班级编号获取班级信息
     * @param cid
     * @return
     */
    Clazz selectByStep1(Integer cid);

    /**
     * 根据班级编号查询班级信息
     * @param cid
     * @return
     */
    Clazz selectByCollection(Integer cid);

    /**
     * 多对一：分布查询第二步:根据cid获取班级信息
     *
     * @param cid
     * @return
     */
    Clazz selectByIdStep2(Integer cid);
}
