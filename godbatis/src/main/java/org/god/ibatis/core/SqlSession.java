package org.god.ibatis.core;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlSession {

    private SqlSessionFactory factory;

    public SqlSession(SqlSessionFactory factory) {
        this.factory = factory;
    }


    /**
     * 执行insert语句，向数据库表当中插入记录
     *
     * @param
     * @return
     */
    public int insert(String sqlId, Object pojo) {
        int count = 0;

        try {
            // JDBC代码，执行insert 语句 ，完成插入操作
            Connection connection = factory.getTransaction().getConnection();
            // insert into t_car values(#{id},#{name},#{age})
            String godbatisSql = factory.getMappedStatements().get(sqlId).getSql();
            System.out.println(factory.getMappedStatements().get(sqlId));
            System.out.println(factory.getMappedStatements().get(sqlId).getSql());
            // insert into t_user values(?,?,?)
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 给 ？ 占位符传值
            // 难度是什么？
            // 第一: 你不知道有多少个？
            // 第二：你不知道该将Pojo对象中的那个属性赋值给哪个？
            // ps.String(第几个问号，传什么值)； // 这里都是setString，所以数据库中的字段类型要求都是varchar才行，
            // 这是godbatis 比较失败的地方
            int formIndex = 0;
            int index = 1;

            while (true) {
                int jingIndex = godbatisSql.indexOf("#", formIndex);
                if (jingIndex < 0) {
                    break;
                }

                System.out.println(index);

                int youKuoHaoIndex = godbatisSql.indexOf("}", formIndex);
                String propertyName = godbatisSql.substring(jingIndex + 2, youKuoHaoIndex).trim();
                System.out.println(propertyName);
                formIndex = youKuoHaoIndex + 1;

                // 有属性名id,怎么获取id 的属性值呢？调用 getId()方法
                String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                Object propertyValue = getMethod.invoke(pojo);
                ps.setString(index,propertyValue.toString());
                index++;

            }
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 给？占位符传值
        return count;
    }


    /**
     * 查询一个对象
     * @param sqlId
     * @param parameterObj
     * @return
     */
    public Object selectOne(String sqlId, Object parameterObj){
        MappedStatement mappedStatement = factory.getMappedStatements().get(sqlId);
        Connection connection = factory.getTransaction().getConnection();
        // 获取sql语句
        String godbatisSql = mappedStatement.getSql();
        String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_\\$]*}", "?");
        // 执行sql
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object obj = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, parameterObj.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                // 将结果集封装对象，通过反射
                String resultType = mappedStatement.getResultType();
                Class<?> aClass = Class.forName(resultType);
                obj = aClass.newInstance();
                // 给对象obj属性赋值
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String setMethodName = "set" + columnName.toUpperCase().charAt(0) + columnName.substring(1);
                    Method setMethod = aClass.getDeclaredMethod(setMethodName, aClass.getDeclaredField(columnName).getType());
                    setMethod.invoke(obj, rs.getString(columnName));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    /**
     * 执行查询语句，返回一个对象，该方法只适合返回一条记录的sql语句
     * @param sqlId
     * @param param
     * @return
     */
    public Object selectOne2(String sqlId,Object param) {
        Object obj = null;
        try {
            Connection connection = factory.getTransaction().getConnection();
            MappedStatement mappedStatement = factory.getMappedStatements().get(sqlId);

            // 这是哪个DQL查询语句
            // select id,name,age from t_user where id = #{id}
            String godbatisSql = mappedStatement.getSql();
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 给占位符传值
            ps.setString(1,param.toString());
            // 查询返回的结果集
            ResultSet rs = ps.executeQuery();
            // 要封装的结果类型
            String resultType = mappedStatement.getResultType();
            // 从结果集中取数据，封装Java对象

            if(rs.next()) {
                // 获取 resultType 的 Class对象
                Class<?> resultTypeClass = Class.forName(resultType);
                // 调用无参数构造方法创建对象
                obj = resultTypeClass.newInstance();  // Object obj = new User();
                // 给User类的id,name,age 属性赋值
                // 给Obj 对象的哪个属性赋哪个值

                /*
                解决问题的关键：将查询结果的列名作为属性名
                列名是id，那么属性就是：id
                列名是name，那么属性名就是：name
                 */
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for (int i = 0;i < columnCount; i++) {
                    String propertyName = rsmd.getColumnName(i+1);
                    // 拼接方法名:
                   String setMethodName =  "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                   // 获取set 方法
                   // Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName,resultTypeClass.getDeclaredField(propertyName).getType());
                    Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName,String.class);
                    // 调用set 方法给对象Obj属性赋值
                    setMethod.invoke(obj,rs.getString(propertyName));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }















    /**
     * 提交事务
     */
    public void commit() {
        factory.getTransaction().commit();
    }


    /**
     * 回滚事务
     */
    public void rollback() {
        factory.getTransaction().rollback();
    }


    /**
     * 关闭事务
     */
    public void close() {
        factory.getTransaction().close();
    }
}
