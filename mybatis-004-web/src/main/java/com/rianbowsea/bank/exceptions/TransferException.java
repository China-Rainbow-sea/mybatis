package com.rianbowsea.bank.exceptions;


/**
 * 转账异常
 */
public class TransferException extends Exception{
    public TransferException() {
    }

    public TransferException(String message) {
        super(message);
    }
}
