package com.rianbowsea.bank.dao.impl;

import com.rianbowsea.bank.dao.AccountDao;
import com.rianbowsea.bank.pojo.Account;
import com.rianbowsea.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoImpl implements AccountDao {
    private SqlSession sqlSession = SqlSessionUtil.openSession();

    @Override
    public Account selectActno(String actno) {
        Account account = (Account) sqlSession.selectOne("account.selectByActno", actno);
        // 注意：事务的控制，都是放在业务层的，不是放在持久层DAo，更不放在utils工具层
        //sqlSession.close();
        return account;
    }

    @Override
    public int updateByActno(Account account) {


        int count = sqlSession.update("account.updateByActno", account);
        // 注意：事务的控制，都是放在业务层的，不是放在持久层DAo，更不放在utils工具层
        //sqlSession.commit();  // 提交数据
        //sqlSession.close();
        return count;
    }
}
