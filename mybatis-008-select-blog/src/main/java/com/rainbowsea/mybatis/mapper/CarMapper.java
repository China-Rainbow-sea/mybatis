package com.rainbowsea.mybatis.mapper;


import com.rainbowsea.mybatis.pojo.Car;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface CarMapper {


    /**
     * mybatis 全局设置，驼峰命名映射
     * @return
     */
    List<Car> selectAllByMapUnderscoreToCamelCase();



    /**
     * 查询所有的Car信息，使用resultMap标签进行结果映射
     * @return
     */
    List<Car> selectAllByResultMap();


    /**
     * 获取Car的总记录条数
     * @return
     */
    Long selectTotal();


    /**
     * 查询所有的Car，返回一个Map集合
     * Map集合的key是每条记录的主键值
     * Map集合的value是每条记录
     * @return
     */
    @MapKey("id") // 将查询结果的id字段的值作为整个Map集合的key。
    Map<Long,Map<String,Object>> selectAllRetMap();





    /**
     * 查询所有的Car 信息，返回一个存放 Map集合大的List集合当中
     * Map集合的key是每条记录的主键值
     * Map集合的value是每条记录
     *
     {128={carType=新能源, carNum=999, guidePrice=21.00, produceTime=2023-03-28, id=128, brand=小米su7},
     129={carType=新能源, carNum=999, guidePrice=21.00, produceTime=2022-03-28, id=129, brand=小米su7},
     130={carType=新能源, carNum=999, guidePrice=21.00, produceTime=2024-03-28, id=130, brand=小米su7},
     131={carType=新能源, carNum=985, guidePrice=3.00, produceTime=2000-10-10, id=131, brand=凯美瑞},
     118={carType=新能源, carNum=999, guidePrice=21.00, produceTime=2021-03-28, id=118, brand=小米su7},
     120={carType=新能源, carNum=999, guidePrice=21.00, produceTime=2022-03-28, id=120, brand=小米su7}}
     */
    List<Map<String,Object>> selectAllRetListMap();




    /**
     *  Mybatis 在 查询结果放到 Map 集合中存放的方式是：
     *  Map<String,     Object>
     *      k           v
     *     "id"         131
     *     "car_num"    999
     *     "brand"      小米su7
     *     查询          对于单个对应字段的值
     *     数据库中
     *     的字段名
     *
     *
     * @param id
     * @return
     */
    Map<String,Object> selectByIdRetMap(Long id);













    /**
     * 如果返回多条记录，采用单个实体类接收会怎样
     *
     * @return
     */

    Car selectAll2();




    /**
     * 获取所有的Car
     *
     * @return
     */
    List<Car> selectAll();


    /**
     * 根据 id 查询 Car 的值
     *
     * @param id
     * @return
     */
    Car selectById(Long id);


}
