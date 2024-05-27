package com.rianbowsea.bank.service;


import com.rianbowsea.bank.exceptions.MoneyNotEnoughException;
import com.rianbowsea.bank.exceptions.TransferException;

/**
 * 注意: 业务类当中的业务方法的名字在起名字的时候，最好见名知意，能够体现出具体的业务是做什么的
 * 账户业务类
 */
public interface AccountService {

    /**
     * 账户转账业务
     *
     * @param fromActno 转出账户
     * @param toActno   转入账户
     * @param money     转账金额
     */
    void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException;

}
