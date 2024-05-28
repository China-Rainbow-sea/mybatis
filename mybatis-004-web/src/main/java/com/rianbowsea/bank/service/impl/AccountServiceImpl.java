package com.rianbowsea.bank.service.impl;

import com.rianbowsea.bank.dao.AccountDao;
import com.rianbowsea.bank.dao.impl.AccountDaoImpl;
import com.rianbowsea.bank.exceptions.MoneyNotEnoughException;
import com.rianbowsea.bank.exceptions.TransferException;
import com.rianbowsea.bank.pojo.Account;
import com.rianbowsea.bank.service.AccountService;
import com.rianbowsea.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {

        // 添加事务控制代码
        SqlSession sqlSession = SqlSessionUtil.openSession();


        // 1.判断转出账户的金额是否充足(select)
        Account fromAct = accountDao.selectActno(fromActno);

        if (fromAct.getBalance() < money) {
            // 2.如果转出账户余额不足，提示用户
            throw new MoneyNotEnoughException("对不起，余额不足");
        }

        // 3. 如果转出账户余额充足，更新转出账户的余额（update）
        // 先在内存当中修改
        Account toACt = accountDao.selectActno(toActno);
        toACt.setBalance(toACt.getBalance() + money);
        fromAct.setBalance(fromAct.getBalance() - money);

        // 4. 更新转入账户的余额(update)
        int count = accountDao.updateByActno(toACt);
        count += accountDao.updateByActno(fromAct);

        // 模拟异常
        String s = null;
        s.toString();

        if(count !=2) {
            throw new TransferException("转账异常，未知原因");
        }

        // 注意：事务的控制，都是放在业务层的，不是放在持久层DAo，更不放在utils工具层
        sqlSession.commit(); // 提交数据
        sqlSession.close();
    }


}
