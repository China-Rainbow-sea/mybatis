package com.rainbowsea.mybatis.mapper;

import com.rainbowsea.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {

    /**
     * 使用Param注解
     * mybatis框架底层的实现原理
     * @param name
     * @param sex
     * @return
     */
    List<Student> selectByNameAndSex2(@Param(value = "name") String name, @Param("sex") Character sex);
















    /**
     * 根据 name 和 sex 查询 Student 信息
     * 如果是多个参数，mybatis 框架底层是怎么做的呢？
     *  mybatis 框架会自动创建一个map集合，并且map集合是以
     *  这种方式存储参数的
     *  map.put("arg0",name)
     *  map.put("arg1",sex)
     *  map.put("param1",name)
     *  map.put("param2",sex)
     *
     * @throws ParseException
     */
    List<Student> selectByNameAndSex(String name,Character sex);











// 单个参数上的处理

    /**
     * 保存学生信息，通过PoJO参数，Student是单个参数，但不是简单类型
     * @param student
     * @return
     */
    int insertStudentByPOJO(Student student);








    /**
     * 保存学生信息，通过Map参数，以下是单参数，但是参数类型不是简单类型，是Map集合
     */
    int insertStudentByMap(Map<String, Object> map);






    /**
     * 当接口中的方法的参数只有一个（单个参数），并且参数的数据类型都是简单类型
     * 根据id查询，name查询，birth查询，sex查询
     */
    List<Student> selectByName(String name);


    List<Student> selectByBirth(Date birth);

    List<Student> selectBySex(Character sex);


    List<Student> selectById(Long id);


}
