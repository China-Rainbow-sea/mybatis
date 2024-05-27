package com.rianbowsea.bank.dao;


import com.rianbowsea.bank.pojo.Account;

/**
 * 账户的DAO对象，负责t_act 表中数据的CRUD，一般一个表对应一个 DAO
 * 强调以下，DAO对象中的任何一个方法和业务不挂钩，没有任何业务逻辑在里头
 * DAo中的方法就是CRUD的，所以方法名大部分是：insertXxx,deletexxx,updatexxx,selectxxx
 */
public interface AccountDao {

    /**
     * 根据账号查询账户信息
     * @param actno 账号
     * @return 账户信息
     */
    Account selectActno(String actno);


    /**
     * 更新账户信息
     * @param account 被更新的账户信息
     * @return 1表示更新成功，其他表示更新失败
     */
    int updateByActno(Account account);


}
