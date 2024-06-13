package org.god.ibatis.core;



import java.util.List;
import java.util.Map;

/**
 * SqlSessionFactory对象；
 * 一个数据库对应一个SqlSessionFactory对象
 * 通过SqlSessionFactory对象可以获取SqlSession对象（开启会话）
 * 一个SqlSessionFactory 对象可以开启对哦个SqlSession 会话
 */
public class SqlSessionFactory {


    /**
     * 获取Sql会话对象
     * @return
     */
    public SqlSession openSession() {

        // 开启会话的前提是开启连接
        transaction.openConnection();
        // 创建SqlSession 对象
        SqlSession sqlSession = new SqlSession(this);
        
        return sqlSession;


    }
    public SqlSessionFactory() {
    }


    /**
     * 事务管理属性
     * 事务管理器可以灵活切换的
     * SqlSessionFactory类中的事务管理器应该是面向接口编程的
     * SqlSessionFactory类中的应该有一个事务管理器接口
     */
     private Transaction transaction;



     /**
     * 数据源属性
     */


    /**
     * 存放SqL语句的Map集合
     * key 是 sqlid
     * value 是对应的 SQL标签信息对象
     */
    private Map<String, MappedStatement> mappedStatements;


    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = this.getMappedStatements();
    }

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatement) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatement;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


}
