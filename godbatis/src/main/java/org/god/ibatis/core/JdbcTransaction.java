package org.god.ibatis.core;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务管理器（godbatis 框架目前只有JdbcTransaction 进行实现）
 */
public class JdbcTransaction implements Transaction{
    /**
     * 数据源属性
     * 经典的设计：面向接口编程
     */
    private DataSource dataSource;


    /**
     * 自动提交标志
     * true 表示自动提交
     * false 表示不采用自动提交
     */
    private boolean autoCommit;


    /**
     * 连接对象
     */
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 创建管理器对象
     * @param dataSource
     * @param autoCommit
     */
    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() {
        if (connection == null) {
            try {
                this.connection = dataSource.getConnection();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
