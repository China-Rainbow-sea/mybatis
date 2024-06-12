package org.god.ibatis.core;


/**
 * 普通的Java类，POJO，封装了一个SQL标签
 * 一个MappedStatement 对象对应一个SQL标签
 * 一个SQL标签中的所有信息封装到MappedStatement对象当中
 * 面向对象编程思想
 */
public class MappedStatement {


    /**
     * sql语句
     */
    private String sql;

    /**
     * 要封装的结果集类型，有的时候 resultType 是 null
     * 比如：insert，delete，update 语句的时候resultType是null
     * 只有当 sql语句 select 语句的时候 resultType 才有值。
     */
    private String resultType;

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
