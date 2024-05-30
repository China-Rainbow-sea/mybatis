package com.rainbowsea.mybatis.test;

import com.rainbowsea.mybatis.mapper.LogMapper;
import com.rainbowsea.mybatis.pojo.Log;
import com.rainbowsea.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class LogMapperTest {

    @Test
    public void testSelectAllByTable() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        LogMapper mapper = sqlSession.getMapper(LogMapper.class);
        List<Log> logs = mapper.selectAllByTable("20220902");
        logs.forEach(log -> {
            System.out.println(log);
        });
    }
}
